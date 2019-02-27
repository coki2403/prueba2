package com.joaquinalejandro.practica2

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView

class PartidaListaHolder(val textView: TextView): RecyclerView.ViewHolder(textView){}

class PartidaListaAdapter(val partidas:List<PartidaLista>):RecyclerView.Adapter<PartidaListaHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartidaListaHolder {
        return PartidaListaHolder(TextView(parent.context))
    }

    override fun getItemCount(): Int =partidas.size

    override fun onBindViewHolder(holder: PartidaListaHolder, position: Int) {
        holder.textView.text="${partidas[position].title}\n ${partidas[position].date.substringBefore("GMT")}\n" +
                "${partidas[position].players}\n"

    }

}