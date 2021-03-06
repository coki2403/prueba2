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
import com.joaquinalejandro.practica2.fragmentos.Lista_fragment
import com.joaquinalejandro.practica2.fragmentos.Tablero_fragment
import com.joaquinalejandro.practica2.fragmentos.Tablero_fragment_online
import com.joaquinalejandro.practica2.vistaRecicladora.IRepositorioPartidas
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista
import kotlinx.android.synthetic.main.fragment_lista_fragment.*


class PartidaListaActivity : AppCompatActivity(), Lista_fragment.OnPartidaListaFragmentInteractionListener,
    Tablero_fragment.OnTableroFragmentInteractionListener, Tablero_fragment_online.OnTableroFragmentInteractionListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partida_lista)

        val fm = supportFragmentManager
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            fm.executeTransaction {
                add(
                    R.id.fragment_container,
                    Lista_fragment()
                )
            }
        }


    }

    override fun onPartidaSelected(partida: PartidaLista) {


        if (findViewById<FrameLayout>(R.id.fragment_container_tablero) != null) {
            val fm = supportFragmentManager
            val tableroFrag: Tablero_fragment

            tableroFrag = Tablero_fragment.newInstance(partida.toJSONString())

            if (fm.findFragmentById(R.id.fragment_container_tablero) == null) {

                fm.executeTransaction { replace(R.id.fragment_container_tablero, tableroFrag) }
            }

        } else {
            val intent = Intent(this, MainActivity::class.java)
            println("sel: ${partida.id}")
            intent.putExtra("ID", partida.toJSONString())
            /*println("enviado: ${intent.extras.getInt("ID")}")*/
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
                                as Lista_fragment
                    roundListFragment.recyclerView.update(
                        SettingsActivity.getPlayerUUID(baseContext).toString(),
                        { partida -> onPartidaSelected(partida) }
                    )
                }
            }
        }
        repository?.addPartida(partida, callback)

    }

    override fun ActualizarListaOnline() {
        if (findViewById<FrameLayout>(R.id.recyclerView) != null) {
            recyclerView.update("RND")
            { round -> onPartidaSelected(round) }
        }
    }
}
