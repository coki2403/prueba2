package com.joaquinalejandro.practica2.activities

import android.support.design.widget.Snackbar
import android.view.View
import es.uam.eps.multij.Movimiento
import com.joaquinalejandro.practica2.model.MovimientoConecta4
import com.joaquinalejandro.practica2.R


import es.uam.eps.multij.*

class ControladorPlayer(var name:String="-"): View.OnClickListener, Jugador {

    private lateinit var game: Partida

    fun setPartida(game: Partida) {
        this.game = game
    }

    override fun onClick(v: View) {
        try {

            if(SettingsActivity.getTurno(v.context)!=game.tablero.turno){
                Snackbar.make(
                    v,"No es tu turno", // ???
                    Snackbar.LENGTH_SHORT
                ).show()
                return
            }

            if (game.tablero.estado != Tablero.EN_CURSO) {
                Snackbar.make(
                    v, R.string.no_en_curso, // ???
                    Snackbar.LENGTH_SHORT
                ).show()
                return
            }
            val dim = v.contentDescription.toString().toInt()
            val m: Movimiento = MovimientoConecta4(dim)
            val a = AccionMover(this, m)
            game.realizaAccion(a)
        } catch (e: Exception) {
            Snackbar.make(v, R.string.invalid_movement, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun getNombre() = name
    override fun puedeJugar(p0: Tablero?) = true
    override fun onCambioEnPartida(p0: Evento) {}


}