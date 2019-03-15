package com.joaquinalejandro.practica2.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
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
        Snackbar.make(recyclerView, "${partida.idC} selected",
            Snackbar.LENGTH_SHORT).show()
        /*Aqui abriemos la partida que queramos*/

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
