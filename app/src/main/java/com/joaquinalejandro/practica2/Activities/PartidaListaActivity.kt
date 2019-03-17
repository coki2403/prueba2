package com.joaquinalejandro.practica2.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.joaquinalejandro.practica2.PartidaAdapter
import com.joaquinalejandro.practica2.PartidaLista
import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.RepositorioPartidas
import kotlinx.android.synthetic.main.activity_partida_lista.*

class PartidaListaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partida_lista)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            itemAnimator = DefaultItemAnimator()
        }

    }

    fun onPartidaSelected(partida:PartidaLista){


        val intent = Intent(this, MainActivity::class.java)
        println("sel: ${partida.idC}")
        intent.putExtra("ID", partida.idC.toInt())
        println("enviado: ${intent.extras.getInt("ID")}")
        startActivity(intent)

    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    fun updateUI(){
        recyclerView.apply {
            if(adapter==null)
                adapter=
                    PartidaAdapter(RepositorioPartidas.partidas){partida->onPartidaSelected(partida)}
            else
                adapter.notifyDataSetChanged()
        }
    }
}
