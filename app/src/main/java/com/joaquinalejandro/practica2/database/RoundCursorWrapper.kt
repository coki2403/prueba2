package com.joaquinalejandro.practica2.database

import android.database.Cursor
import android.database.CursorWrapper
import android.util.Log
import com.joaquinalejandro.practica2.model.TableroConecta4
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista
import es.uam.eps.multij.ExcepcionJuego

class RoundCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    private val DEBUG = "DEBUG"
    val round: PartidaLista
        get() {
            val playername = getString(getColumnIndex(PartidaDataBaseSchema.UserTable.Cols.PLAYERNAME))
            val playeruuid = getString(getColumnIndex(PartidaDataBaseSchema.UserTable.Cols.PLAYERUUID))
            val rounduuid = getString(getColumnIndex(PartidaDataBaseSchema.RoundTable.Cols.ROUNDUUID))
            val date = getString(getColumnIndex(PartidaDataBaseSchema.RoundTable.Cols.DATE))
            val title = getString(getColumnIndex(PartidaDataBaseSchema.RoundTable.Cols.TITLE))
            val filas = getString(getColumnIndex(PartidaDataBaseSchema.RoundTable.Cols.FILAS))
            val columnas = getString(getColumnIndex(PartidaDataBaseSchema.RoundTable.Cols.COLUMNAS))
            val board = getString(getColumnIndex(PartidaDataBaseSchema.RoundTable.Cols.BOARD))
            val round = PartidaLista(
                filas.toInt(),columnas.toInt()
            )
            round.firstPlayerName = "Random"
            round.firstPlayerUUID = "Random"
            round.secondPlayerName = playername
            round.secondPlayerUUID = playeruuid
            round.id = rounduuid
            round.date = date
            round.title = title
            try {
                round.board=board
            } catch (e: ExcepcionJuego) {
                Log.d(DEBUG, "Error turning string into tablero")
            }
            return round
        }


}