package com.joaquinalejandro.practica2.activities


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.FrameLayout
import android.widget.Toast

import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.database.PartidaRepositoryFactory
import com.joaquinalejandro.practica2.extras.executeTransaction
import com.joaquinalejandro.practica2.extras.update
import com.joaquinalejandro.practica2.fragmentos.lista_fragment
import com.joaquinalejandro.practica2.fragmentos.tablero_fragment
import com.joaquinalejandro.practica2.vistaRecicladora.IRepositorioPartidas
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista
import kotlinx.android.synthetic.main.fragment_lista_fragment.*


class MainActivity : AppCompatActivity(), lista_fragment.OnPartidaListaFragmentInteractionListener,
    tablero_fragment.OnTableroFragmentInteractionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager
        val tableroFrag: tablero_fragment

        /*Comprobacion para cargar la aprtida*/
        if (intent.extras != null) {
            val idPartida = intent.extras.getString("ID")
            tableroFrag = tablero_fragment.newInstance(idPartida)
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

            tableroFrag = tablero_fragment.newInstance(partida.id)

            if (fm.findFragmentById(R.id.fragment_container_tablero) != null) {

                fm.executeTransaction { replace(R.id.fragment_container_tablero, tableroFrag) }
            }

        } else {
            val intent = Intent(this, MainActivity::class.java)
            println("sel: ${partida.id}")
            intent.putExtra("ID", partida.id.toInt())
            println("enviado: ${intent.extras.getInt("ID")}")
            startActivity(intent)
        }


    }


    override fun onReiniciar() {
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

    override fun onActualizaLista(partida: PartidaLista) {
        val repository = PartidaRepositoryFactory.createRepository(this)
        val callback = object : IRepositorioPartidas.BooleanCallback {
            override fun onResponse(response: Boolean) {
                if (response == true) {
                    /*recyclerView.update(
                        "Random",
                        { partida -> onPartidaSelected(partida) }
                    )*/
                } else
                    /*Toast.makeText(
                        this,
                        "Comenzada nueva partida", Toast.LENGTH_SHORT
                    ).show()*/
                    println("Error actualizando")
            }
        }
        println("ID:"+partida.id)
        repository?.actualizarPartida(partida, callback)

    }


    override fun onPreferenceSelected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNewRoundAdded() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}
