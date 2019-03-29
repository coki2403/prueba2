package com.joaquinalejandro.practica2.activities


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.joaquinalejandro.practica2.*
import com.joaquinalejandro.practica2.extras.executeTransaction
import com.joaquinalejandro.practica2.fragmentos.lista_fragment
import com.joaquinalejandro.practica2.fragmentos.tablero_fragment
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista


class PartidaListaActivity : AppCompatActivity(), lista_fragment.OnPartidaListaFragmentInteractionListener {

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

            tableroFrag = tablero_fragment.newInstance(partida.idC)

            if (fm.findFragmentById(R.id.fragment_container_tablero) == null) {

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
