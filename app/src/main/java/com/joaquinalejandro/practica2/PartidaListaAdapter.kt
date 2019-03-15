package com.joaquinalejandro.practica2

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import es.uam.eps.multij.Partida


class PartidaListaHolder(val textView: TextView): RecyclerView.ViewHolder(textView), View.OnClickListener {
    override fun onClick(v: View?) {
        /*Snackbar.make(itemView, "Item ${idTextView.text} selected",
            Snackbar.LENGTH_SHORT).show()*/
    }
/*
    lateinit var idTextView: TextView
    lateinit var jugadoresTextView: TextView
    lateinit var dateTextView: TextView

    init {
        idTextView = itemView.findViewById(R.id.list_item_id) as TextView
        jugadoresTextView = itemView.findViewById(R.id.list_item_board) as TextView
        dateTextView = itemView.findViewById(R.id.list_item_date) as TextView
    }
    fun vincularPartida(partida: Partida) {
        // idTextView.text = Partida.id
        // jugadoresTextView.text = Partida.jugadores
        // dateTextView.text = Partida.date.toString().substring(0,19)
    }*/
}

class PartidaListaAdapter(val partidas:List<PartidaLista>):RecyclerView.Adapter<PartidaListaHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartidaListaHolder {
        return PartidaListaHolder(TextView(parent.context))
    }

    override fun getItemCount(): Int =partidas.size

    override fun onBindViewHolder(holder: PartidaListaHolder, position: Int) {
       /* holder.textView.text="${partidas[position].title}\n ${partidas[position].date.substringBefore("GMT")}\n" +
                "${partidas[position].players}\n"*/

    }

}