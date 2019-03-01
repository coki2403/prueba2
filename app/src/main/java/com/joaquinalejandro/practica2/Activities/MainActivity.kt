package com.joaquinalejandro.practica2.Activities


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.model.JugadorJuego
import com.joaquinalejandro.practica2.model.ObservadorJuego
import com.joaquinalejandro.practica2.model.TableroJuego

import es.uam.eps.multij.JugadorAleatorio
import es.uam.eps.multij.Jugador
import es.uam.eps.multij.Partida


class MainActivity : AppCompatActivity() {


    var ids: Array<Array<ImageView>>? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ids = arrayOf(
            arrayOf(f00, f01, f02, f03, f04, f05, f06),
            arrayOf(f10, f11, f12, f13, f14, f15, f16),
            arrayOf(f20, f21, f22, f23, f24, f25, f26),
            arrayOf(f30, f31, f32, f33, f34, f35, f36),
            arrayOf(f40, f41, f42, f43, f44, f45, f46),
            arrayOf(f50, f51, f52, f53, f54, f55, f56)
        )

        val listaJugadores = arrayListOf<Jugador>()
        listaJugadores.add(JugadorJuego("Antonio"))
        listaJugadores.add(JugadorAleatorio("maquina"))

        val partida= Partida(TableroJuego(5, 7),listaJugadores)
        partida.addObservador(ObservadorJuego())

        partida.comenzar()

    }

    fun onButtonClicked(view: View) {
        f50.setImageDrawable(getDrawable(R.drawable.circulo_amarillo))
        val col=view.contentDescription.toString().toInt();
        ids!!.get(5)[col].setImageDrawable(getDrawable(R.drawable.circulo_amarillo))
    }

    fun aPartidas() {
        startActivity(Intent(this@MainActivity, PartidaListaActivity::class.java))
    }
}
