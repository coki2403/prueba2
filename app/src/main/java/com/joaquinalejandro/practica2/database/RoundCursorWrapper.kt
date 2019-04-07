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
            val size = getString(getColumnIndex(PartidaDataBaseSchema.RoundTable.Cols.SIZE))
            val board = getString(getColumnIndex(PartidaDataBaseSchema.RoundTable.Cols.BOARD))
            val round = PartidaLista(rounduuid,date,"jugadores",board,
                TableroConecta4(0,0),"Random",playername,"Random",playeruuid)
            round.firstPlayerName = "Random"
            round.firstPlayerUUID = "Random"
            round.secondPlayerName = playername
            round.secondPlayerUUID = playeruuid
            round.idC = rounduuid
            round.dateC = date
            try {
                round.tabC.stringToTablero(board)
            } catch (e: ExcepcionJuego) {
                Log.d(DEBUG, "Error turning string into tablero")
            }
            return round
        }
}