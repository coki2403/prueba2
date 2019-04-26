package com.joaquinalejandro.practica2.activities


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.FrameLayout
import com.joaquinalejandro.practica2.*
import com.joaquinalejandro.practica2.database.PartidaRepositoryFactory
import com.joaquinalejandro.practica2.extras.executeTransaction
import com.joaquinalejandro.practica2.extras.update
import com.joaquinalejandro.practica2.fragmentos.lista_fragment
import com.joaquinalejandro.practica2.fragmentos.tablero_fragment
import com.joaquinalejandro.practica2.vistaRecicladora.IRepositorioPartidas
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista
import es.uam.eps.multij.Partida
import kotlinx.android.synthetic.main.fragment_lista_fragment.*


class PartidaListaActivity : AppCompatActivity(), lista_fragment.OnPartidaListaFragmentInteractionListener,
    tablero_fragment.OnTableroFragmentInteractionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partida_lista)

        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            fm.executeTransaction {
                add(
                    R.id.fragment_container,
                    lista_fragment()
                )
            }
        }

    }

    override fun onPartidaSelected(partida: PartidaLista) {


        if (findViewById<FrameLayout>(R.id.fragment_container_tablero) != null) {
            val fm = supportFragmentManager
            val tableroFrag: tablero_fragment

            tableroFrag = tablero_fragment.newInstance(partida.id)

            if (fm.findFragmentById(R.id.fragment_container_tablero) == null) {

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActualizaLista(partida: PartidaLista) {
        val repository = PartidaRepositoryFactory.createRepository(this)
        val callback = object : IRepositorioPartidas.BooleanCallback {
            override fun onResponse(response: Boolean) {
                if (response == true) {
                    recyclerView.update(
                        "Random",
                        { partida -> onPartidaSelected(partida) }
                    )
                } else
                    Snackbar.make(findViewById(R.id.title),
                        R.string.error_updating_round,
                        Snackbar.LENGTH_LONG).show()
            }
        }
        repository?.actualizarPartida(partida, callback)

    }


    override fun onPreferenceSelected() {

    }

    override fun onNewRoundAdded() {


        val partida = PartidaLista(6,7)

        val repository = PartidaRepositoryFactory.createRepository(this)
        val callback = object : IRepositorioPartidas.BooleanCallback {
            override fun onResponse(response: Boolean) {
                if (response == false)
                    Snackbar.make(findViewById(R.id.recyclerView),
                        R.string.error_adding_round, Snackbar.LENGTH_LONG).show()
                else {
                    Snackbar.make(findViewById(R.id.recyclerView),
                        "New " + partida.id + " added", Snackbar.LENGTH_LONG).show()
                    val fragmentManager = supportFragmentManager
                    val roundListFragment = fragmentManager.findFragmentById(R.id.fragment_container)
                                as lista_fragment
                    roundListFragment.recyclerView.update(
                        SettingsActivity.getPlayerUUID(baseContext).toString(),
                        { partida -> onPartidaSelected(partida) }
                    )
                }
            }
        }
        repository?.addPartida(partida, callback)

    }


}
