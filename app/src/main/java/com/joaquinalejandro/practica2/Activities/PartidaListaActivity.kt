package com.joaquinalejandro.practica2.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.joaquinalejandro.practica2.PartidaListaAdapter
import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.RepositorioPartidas
import kotlinx.android.synthetic.main.activity_partida_lista.*

class PartidaListaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partida_lista)
    }

    override fun onResume() {
        super.onResume()
    }

    fun updateUI(){
        recyclerView.apply {
            if(adapter==null)
                adapter=
                    PartidaListaAdapter(RepositorioPartidas.partidas)
            else
                adapter.notifyDataSetChanged()
        }
    }
}
