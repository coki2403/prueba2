package com.joaquinalejandro.practica2

import android.support.design.widget.Snackbar
import android.view.View
import es.uam.eps.multij.Movimiento
import com.joaquinalejandro.practica2.R


import es.uam.eps.multij.*

class ControladorPlayer: View.OnClickListener, Jugador {
    //private val ids = arrayOf(intArrayOf(R.id.er1, R.id.er2, R.id.er3),
    //    intArrayOf(R.id.er4, R.id.er5, R.id.er6),
    //    intArrayOf(R.id.er7, R.id.er8, R.id.er9))
    private lateinit var game: Partida
    fun setPartida(game: Partida) {
        this.game = game
    }
    override fun onClick(v: View) {
        try {
            if (game.tablero.estado != Tablero.EN_CURSO) {
                //Snackbar.make(v, R.string.round_already_finished,
                    //Snackbar.LENGTH_SHORT).show()
                return
            }
            //val m: Movimiento = Movimiento(fromViewToI(v), fromViewToJ(v))
            //val a = AccionMover(this, m)
            //game.realizaAccion(a)
        } catch (e: Exception) {
            //Snackbar.make(v, R.string.invalid_movement,
                //Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun getNombre() = "ER local player"
    override fun puedeJugar(p0: Tablero?) = true
    override fun onCambioEnPartida(p0: Evento) {}
    /*private fun fromViewToI(view: View): Int {
        for (i in 0 until ids.size)
            for (j in 0 until ids.size) {
                if (view.id == ids[i][j])
                    return i
            }
        return -1
    }
    private fun fromViewToJ(view: View): Int {
        for (i in 0 until ids.size)
            for (j in 0 until ids.size)
                if (view.id == ids[i][j])
                    return j
        return -1
    }*/
}