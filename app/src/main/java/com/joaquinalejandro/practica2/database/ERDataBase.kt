package com.joaquinalejandro.practica2.database

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.joaquinalejandro.practica2.vistaRecicladora.IRepositorioPartidas
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista
import java.lang.Exception
import java.util.*

class ERDataBase(context: Context) : IRepositorioPartidas {
    override fun open() {
        db = helper.writableDatabase
    }

    override fun close() {
        db?.close()
    }

    override fun login(playername: String, password: String, callback: IRepositorioPartidas.LoginRegisterCallback) {
        Log.d(DEBUG_TAG, "Login $playername")
        try {
            val cursor = db!!.query(
                PartidaDataBaseSchema.UserTable.NAME,
                arrayOf(PartidaDataBaseSchema.UserTable.Cols.PLAYERUUID),
                PartidaDataBaseSchema.UserTable.Cols.PLAYERNAME + " = ? AND "
                        + PartidaDataBaseSchema.UserTable.Cols.PLAYERPASSWORD + " = ?",
                arrayOf(playername, password),
                null, null, null
            )
            val count = cursor.count
            val uuid =
                if (count == 1 && cursor.moveToFirst()) cursor.getString(0) else ""
            cursor.close()
            if (count == 1)
                callback.onLogin(uuid)
            else
                callback.onError("Username or password incorrect.")
        }catch(e:Exception){
            callback.onError("Username or password incorrect.")
        }

    }

    override fun register(playername: String, password: String, callback: IRepositorioPartidas.LoginRegisterCallback) {
        val values = ContentValues()
        val uuid = UUID.randomUUID().toString()
        values.put(PartidaDataBaseSchema.UserTable.Cols.PLAYERUUID, uuid)
        values.put(PartidaDataBaseSchema.UserTable.Cols.PLAYERNAME, playername)
        values.put(PartidaDataBaseSchema.UserTable.Cols.PLAYERPASSWORD, password)
        try {
            val id = db!!.insert(PartidaDataBaseSchema.UserTable.NAME, null, values)
            if (id < 0)
                callback.onError("Error inserting new player named $playername")
            else
                callback.onLogin(uuid)
        }catch(e:Exception){
            callback.onError("Error inserting new player named $playername")
        }



    }

    override fun getPartidas(
        playeruuid: String,
        orderByField: String,
        group: String,
        callback: IRepositorioPartidas.RoundsCallback
    ) {
        val rounds = ArrayList<PartidaLista>()
        val cursor = queryRounds()

        cursor!!.moveToFirst()
        while (!cursor.isAfterLast()) {
            val round = cursor.round
            if (round.secondPlayerUUID.equals(playeruuid) || true)
                rounds.add(round)
            cursor.moveToNext()
        }
        cursor.close()
        if (cursor.getCount() > 0)

            callback.onResponse(rounds)
        else
            callback.onError("No rounds found in database")
    }


    private fun queryRounds(): RoundCursorWrapper? {
        val sql = "SELECT " + PartidaDataBaseSchema.UserTable.Cols.PLAYERNAME + ", " +
                PartidaDataBaseSchema.UserTable.Cols.PLAYERUUID + ", " +
                PartidaDataBaseSchema.RoundTable.Cols.ROUNDUUID + ", " +
                PartidaDataBaseSchema.RoundTable.Cols.DATE + ", " +
                PartidaDataBaseSchema.RoundTable.Cols.TITLE + ", " +
                PartidaDataBaseSchema.RoundTable.Cols.FILAS + ", " +
                PartidaDataBaseSchema.RoundTable.Cols.COLUMNAS + ", " +
                PartidaDataBaseSchema.RoundTable.Cols.BOARD + " " +
                "FROM " + PartidaDataBaseSchema.UserTable.NAME + " AS p, " +
                PartidaDataBaseSchema.RoundTable.NAME + " AS r " +"WHERE " +
                "p." + PartidaDataBaseSchema.UserTable.Cols.PLAYERUUID + "=" +
                "r." + PartidaDataBaseSchema.RoundTable.Cols.PLAYERUUID+ ";"
        val cursor = db!!.rawQuery(sql, null)
        return RoundCursorWrapper(cursor)
    }

    override fun addPartida(round: PartidaLista, callback: IRepositorioPartidas.BooleanCallback) {
        val values = getContentValues(round)
        val id = db!!.insert(PartidaDataBaseSchema.RoundTable.NAME, null, values)
        callback.onResponse(id >= 0)

    }

    private fun getContentValues(round: PartidaLista): ContentValues {
        val values = ContentValues()
        values.put(PartidaDataBaseSchema.RoundTable.Cols.PLAYERUUID, round.firstPlayerUUID)
        values.put(PartidaDataBaseSchema.RoundTable.Cols.ROUNDUUID, round.id)
        values.put(PartidaDataBaseSchema.RoundTable.Cols.DATE, round.date)
        values.put(PartidaDataBaseSchema.RoundTable.Cols.TITLE, round.title)
        values.put(PartidaDataBaseSchema.RoundTable.Cols.FILAS, round.fil)
        values.put(PartidaDataBaseSchema.RoundTable.Cols.COLUMNAS, round.col)
        values.put(PartidaDataBaseSchema.RoundTable.Cols.BOARD, round.board)
        return values
    }


    override fun actualizarPartida(round: PartidaLista, callback: IRepositorioPartidas.BooleanCallback) {
        val values = getContentValues(round)
        val id = db!!.update(
            PartidaDataBaseSchema.RoundTable.NAME, values,
            PartidaDataBaseSchema.RoundTable.Cols.ROUNDUUID + " = ?",
            arrayOf(round.id))
        callback.onResponse(id >= 0)


    }

    private val DEBUG_TAG = "DEBUG"
    private val helper: DatabaseHelper
    private var db: SQLiteDatabase? = null

    init {
        helper = DatabaseHelper(context)
    }

    private class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            createTable(db)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS " + PartidaDataBaseSchema.UserTable.NAME)
            db.execSQL("DROP TABLE IF EXISTS " + PartidaDataBaseSchema.RoundTable.NAME)
            createTable(db)
        }

        private fun createTable(db: SQLiteDatabase) {
            val str1 = ("CREATE TABLE " + PartidaDataBaseSchema.UserTable.NAME + " ("
                    + "_id integer primary key autoincrement, "
                    + PartidaDataBaseSchema.UserTable.Cols.PLAYERUUID + " TEXT UNIQUE, "
                    + PartidaDataBaseSchema.UserTable.Cols.PLAYERNAME + " TEXT UNIQUE, "
                    + PartidaDataBaseSchema.UserTable.Cols.PLAYERPASSWORD + " TEXT);")
            val str2 = ("CREATE TABLE " + PartidaDataBaseSchema.RoundTable.NAME + " ("
                    + "_id integer primary key autoincrement, "
                    + PartidaDataBaseSchema.RoundTable.Cols.ROUNDUUID + " TEXT UNIQUE, "
                    + PartidaDataBaseSchema.RoundTable.Cols.PLAYERUUID + " TEXT REFERENCES "
                    + PartidaDataBaseSchema.UserTable.Cols.PLAYERUUID + ", "
                    + PartidaDataBaseSchema.RoundTable.Cols.DATE + " TEXT, "
                    + PartidaDataBaseSchema.RoundTable.Cols.TITLE + " TEXT, "
                    + PartidaDataBaseSchema.RoundTable.Cols.FILAS + " TEXT, "
                    + PartidaDataBaseSchema.RoundTable.Cols.COLUMNAS + " TEXT, "
                    + PartidaDataBaseSchema.RoundTable.Cols.BOARD + " TEXT);")
            try {
                db.execSQL(str1)
                db.execSQL(str2)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

    }


    companion object {
        private val DATABASE_NAME = "er.db"
        private val DATABASE_VERSION = 1
    }
}
