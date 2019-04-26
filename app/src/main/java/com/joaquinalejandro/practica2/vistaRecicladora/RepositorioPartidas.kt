package com.joaquinalejandro.practica2.vistaRecicladora

import com.joaquinalejandro.practica2.database.RoundCursorWrapper
import com.joaquinalejandro.practica2.model.TableroConecta4
import com.joaquinalejandro.practica2.model.guardarPartida
import es.uam.eps.multij.Partida
import java.util.*

interface IRepositorioPartidas {
    @Throws(Exception::class)
    fun open()

    fun close()
    interface LoginRegisterCallback {
        fun onLogin(playerUuid: String)
        fun onError(error: String)
    }

    fun login(playername: String, password: String, callback: LoginRegisterCallback)

    fun register(playername: String, password: String, callback: LoginRegisterCallback)

    interface BooleanCallback {
        fun onResponse(ok: Boolean)
    }

    fun getPartidas(
        playeruuid: String, orderByField: String, group: String,
        callback: RoundsCallback
    )

    fun addPartida(round: PartidaLista, callback: BooleanCallback)
    fun actualizarPartida(round: PartidaLista, callback: BooleanCallback)
    interface RoundsCallback {
        fun onResponse(rounds: List<PartidaLista>)
        fun onError(error: String)
    }
}

/*
object RepositorioPartidas {


    val partidas = ArrayList<PartidaLista>()
    var numPartidas = 0

    init {
        /*for(i in 0..100)
            partidas.add(PartidaLista(i.toString(), Date().toString(),"Jugador1 vd maquina"))*/
    }

    fun addPartida(partida: Partida): Int {

        val jugadores = partida.getJugador(0).nombre + " vs " + partida.getJugador(1).nombre
        partidas.add(
            PartidaLista(
                numPartidas.toString(),
                Date().toString(),
                jugadores,
                partida.guardarPartida(),
                partida.tablero as TableroConecta4, "", "", "", ""
            )
        )
        numPartidas++
        return (numPartidas - 1)
    }

    fun actualizarPartida(id: Int, partida: Partida) {

        val fecha = partidas.get(id).dateC
        val jugadores = partida.getJugador(0).nombre + " vs " + partida.getJugador(1).nombre
        partidas[id] =
            PartidaLista(
                id.toString(), fecha, jugadores, partida.guardarPartida(),
                partida.tablero as TableroConecta4, "", "", "", ""
            )
    }

    fun getPartida(id: Int): String {
        val partida = partidas[id]
        return partida.tablero

    }





}*/