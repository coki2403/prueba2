package com.joaquinalejandro.practica2.activities

import android.support.design.widget.Snackbar
import android.view.View
import es.uam.eps.multij.Movimiento
import com.joaquinalejandro.practica2.model.MovimientoJuego
import com.joaquinalejandro.practica2.R


import es.uam.eps.multij.*

class ControladorPlayer: View.OnClickListener, Jugador {

    private lateinit var game: Partida

    fun setPartida(game: Partida) {
        this.game = game
    }
    override fun onClick(v: View) {
        try {
            if (game.tablero.estado != Tablero.EN_CURSO) {
                Snackbar.make(v, R.string.no_en_curso, // ???
                    Snackbar.LENGTH_SHORT).show()
                return
            }
            val dim=v.contentDescription.toString().toInt()
            val m: Movimiento = MovimientoJuego(dim)
            val a = AccionMover(this, m)
            game.realizaAccion(a)
        } catch (e: Exception) {
            Snackbar.make(v, R.string.invalid_movement,Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun getNombre() = "ER local player"
    override fun puedeJugar(p0: Tablero?) = true
    override fun onCambioEnPartida(p0: Evento) {}


}