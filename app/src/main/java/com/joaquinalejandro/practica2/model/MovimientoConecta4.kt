package com.joaquinalejandro.practica2.model

import es.uam.eps.multij.Movimiento

class MovimientoConecta4(var dim: Int) : Movimiento() {
    override fun equals(other: Any?): Boolean {
        try {
            if ((other as MovimientoConecta4).dim == dim)
                return true

            return false
        } catch (e: Exception) {
            return false
        }
    }


    override fun toString() = dim.toString()

}