package com.joaquinalejandro.practica2.model

import es.uam.eps.multij.Evento
import es.uam.eps.multij.Jugador
import es.uam.eps.multij.Tablero

class JugadorRemoto(var nombreJugador:String): Jugador {
    override fun getNombre(): String {
        return nombreJugador
    }

    override fun puedeJugar(p0: Tablero?): Boolean {
        return true
    }

    override fun onCambioEnPartida(p0: Evento?) {
        print("onCambioEnPartida")
    }


}