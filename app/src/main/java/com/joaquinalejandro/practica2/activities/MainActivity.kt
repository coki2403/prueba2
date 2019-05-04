package com.joaquinalejandro.practica2.activities


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout

import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.database.PartidaRepositoryFactory
import com.joaquinalejandro.practica2.extras.executeTransaction
import com.joaquinalejandro.practica2.fragmentos.Lista_fragment
import com.joaquinalejandro.practica2.fragmentos.Tablero_fragment
import com.joaquinalejandro.practica2.fragmentos.Tablero_fragment_online
import com.joaquinalejandro.practica2.vistaRecicladora.IRepositorioPartidas
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista


class MainActivity : AppCompatActivity(), Lista_fragment.OnPartidaListaFragmentInteractionListener,
    Tablero_fragment.OnTableroFragmentInteractionListener, Tablero_fragment_online.OnTableroFragmentInteractionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager


        if(SettingsActivity.getTipoBd(this)=="LOCAL"){
            val tableroFrag: Tablero_fragment
            /*Comprobacion para cargar la aprtida*/
            if (intent.extras != null) {
                val idPartida = intent.extras.getString("ID")
                tableroFrag = Tablero_fragment.newInstance(idPartida)
            } else {
                tableroFrag = Tablero_fragment.newInstance("-1")
            }



            if (fm.findFragmentById(R.id.fragment_container_tablero) == null) {

                fm.executeTransaction { add(R.id.fragment_container_tablero, tableroFrag) }
            }
        }else{
            val tableroFragOnline: Tablero_fragment_online
            /*Comprobacion para cargar la aprtida*/
            if (intent.extras != null) {
                val idPartida = intent.extras.getString("ID")
                tableroFragOnline = Tablero_fragment_online.newInstance(idPartida)
            } else {
                tableroFragOnline = Tablero_fragment_online.newInstance("-1")
            }



            if (fm.findFragmentById(R.id.fragment_container_tablero) == null) {

                fm.executeTransaction { add(R.id.fragment_container_tablero, tableroFragOnline) }
            }
        }


        if (findViewById<FrameLayout>(R.id.fragment_container_lista) != null) {
            if (fm.findFragmentById(R.id.fragment_container_lista) == null) {

                fm.executeTransaction {
                    add(
                        R.id.fragment_container_lista,
                        Lista_fragment()
                    )
                }
            }
        }


    }

    override fun onPartidaSelected(partida: PartidaLista) {


        if (findViewById<FrameLayout>(R.id.fragment_container_tablero) != null) {
            val fm = supportFragmentManager
            val tableroFrag: Tablero_fragment

            tableroFrag = Tablero_fragment.newInstance(partida.id)

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
            val tableroFrag: Tablero_fragment

            tableroFrag = Tablero_fragment.newInstance("-1")

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
