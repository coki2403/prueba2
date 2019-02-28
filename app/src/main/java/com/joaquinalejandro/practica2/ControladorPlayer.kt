package com.joaquinalejandro.practica2

import android.support.design.widget.Snackbar
import android.view.View
import es.uam.eps.multij.Movimiento
import com.joaquinalejandro.practica2.R


import es.uam.eps.multij.*
import kotlinx.android.synthetic.main.activity_main.*

class ControladorPlayer: View.OnClickListener, Jugador {
    private val ids = arrayOf(
        arrayOf(R.id.f00, R.id.f01, R.id.f02, R.id.f03, R.id.f04, R.id.f05, R.id.f06),
        arrayOf(R.id.f10, R.id.f11, R.id.f12, R.id.f13, R.id.f14, R.id.f15, R.id.f16),
        arrayOf(R.id.f20, R.id.f21, R.id.f22, R.id.f23, R.id.f24, R.id.f25, R.id.f26),
        arrayOf(R.id.f30, R.id.f31, R.id.f32, R.id.f33, R.id.f34, R.id.f35, R.id.f36),
        arrayOf(R.id.f40, R.id.f41, R.id.f42, R.id.f43, R.id.f44, R.id.f45, R.id.f46),
        arrayOf(R.id.f50, R.id.f51, R.id.f52, R.id.f53, R.id.f54, R.id.f55, R.id.f56)
    )
    private lateinit var game: Partida
    fun setPartida(game: Partida) {
        this.game = game
    }
    override fun onClick(v: View) {
        try {
            if (game.tablero.estado != Tablero.EN_CURSO) {
                Snackbar.make(v, R.string.abc_action_bar_up_description, // ???
                    Snackbar.LENGTH_SHORT).show()
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
    private fun fromViewToI(view: View): Int {
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
    }
}