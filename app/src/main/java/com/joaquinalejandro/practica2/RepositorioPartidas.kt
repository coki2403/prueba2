package com.joaquinalejandro.practica2

import com.joaquinalejandro.practica2.model.TableroJuego
import com.joaquinalejandro.practica2.model.guardarPartida
import es.uam.eps.multij.Partida
import java.lang.Exception
import java.util.*

object RepositorioPartidas {
    val partidas=ArrayList<PartidaLista>()
    var numPartidas=0
    init {
        /*for(i in 0..100)
            partidas.add(PartidaLista(i.toString(), Date().toString(),"Jugador1 vd maquina"))*/
    }

    fun addPartida(partida: Partida):Int{

        val jugadores=partida.getJugador(0).nombre+" vs "+partida.getJugador(1).nombre
        partidas.add(PartidaLista(numPartidas.toString(), Date().toString(),jugadores,partida.guardarPartida(),
            partida.tablero as TableroJuego
        ))
        numPartidas++
        return (numPartidas-1)
    }

    fun actualizarPartida(id:Int,partida:Partida){

        val fecha= partidas.get(id).dateC
        val jugadores=partida.getJugador(0).nombre+" vs "+partida.getJugador(1).nombre
        partidas[id]=PartidaLista(id.toString(), fecha,jugadores,partida.guardarPartida(),
            partida.tablero as TableroJuego)
    }

    fun getPartida(id:Int):String{
        val partida = partidas[id]
        return partida.tablero

    }

}