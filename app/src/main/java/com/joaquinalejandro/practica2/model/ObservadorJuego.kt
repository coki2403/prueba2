package com.joaquinalejandro.practica2.model

import es.uam.eps.multij.Evento
import es.uam.eps.multij.PartidaListener

class ObservadorJuego:PartidaListener{

    override fun onCambioEnPartida(evento: Evento?) {
        when(evento?.tipo){
            Evento.EVENTO_CAMBIO->{
                println(evento.descripcion)
                println(evento.partida.tablero.toString())
            }
            Evento.EVENTO_FIN->{
                println(evento.partida.tablero.toString())
                println(evento.descripcion)
            }

        }
    }


}