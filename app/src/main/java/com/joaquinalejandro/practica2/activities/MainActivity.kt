package com.joaquinalejandro.practica2.activities


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout

import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.extras.executeTransaction
import com.joaquinalejandro.practica2.fragmentos.lista_fragment
import com.joaquinalejandro.practica2.fragmentos.tablero_fragment
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista


class MainActivity : AppCompatActivity(), lista_fragment.OnPartidaListaFragmentInteractionListener,
    tablero_fragment.OnTableroFragmentInteractionListener {


    override fun reiniciar() {
        if (findViewById<FrameLayout>(R.id.fragment_container_tablero) != null) {
            val fm = supportFragmentManager
            val tableroFrag: tablero_fragment

            tableroFrag = tablero_fragment.newInstance("-1")

            if (fm.findFragmentById(R.id.fragment_container_tablero) != null) {

                fm.executeTransaction { replace(R.id.fragment_container_tablero, tableroFrag) }
            }

        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager
        val tableroFrag: tablero_fragment

        /*Comprobacion para cargar la aprtida*/
        if (intent.extras != null) {
            val idPartida = intent.extras.getInt("ID")
            tableroFrag = tablero_fragment.newInstance(idPartida.toString())
        } else {
            tableroFrag = tablero_fragment.newInstance("-1")
        }



        if (fm.findFragmentById(R.id.fragment_container_tablero) == null) {

            fm.executeTransaction { add(R.id.fragment_container_tablero, tableroFrag) }
        }

        if (findViewById<FrameLayout>(R.id.fragment_container_lista) != null) {
            if (fm.findFragmentById(R.id.fragment_container_lista) == null) {

                fm.executeTransaction {
                    add(
                        R.id.fragment_container_lista,
                        lista_fragment()
                    )
                }
            }
        }


    }

    override fun onPartidaSelected(partida: PartidaLista) {


        if (findViewById<FrameLayout>(R.id.fragment_container_tablero) != null) {
            val fm = supportFragmentManager
            val tableroFrag: tablero_fragment

            tableroFrag = tablero_fragment.newInstance(partida.idC)

            if (fm.findFragmentById(R.id.fragment_container_tablero) != null) {

                fm.executeTransaction { replace(R.id.fragment_container_tablero, tableroFrag) }
            }

        } else {
            val intent = Intent(this, MainActivity::class.java)
            println("sel: ${partida.idC}")
            intent.putExtra("ID", partida.idC.toInt())
            println("enviado: ${intent.extras.getInt("ID")}")
            startActivity(intent)
        }


    }

}
