package com.joaquinalejandro.practica2

import java.lang.Exception
import java.util.*

object RepositorioPartidas {
    val partidas=ArrayList<PartidaLista>()
    init {
        for(i in 0..100)
            partidas.add(PartidaLista(i.toString(),"partida", Date().toString(),"Jugador1 jugador2"))
    }

    fun getPartida(id:String):PartidaLista{
        val partida = partidas.find{it.id==id}
        return partida?: throw Exception("No existe la partida")

    }

}