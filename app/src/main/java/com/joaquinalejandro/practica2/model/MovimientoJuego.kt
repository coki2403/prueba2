package com.joaquinalejandro.practica2.model

import es.uam.eps.multij.Movimiento

class MovimientoJuego (var dim: Int): Movimiento(){
    override fun equals(other: Any?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun toString()= dim.toString()

}