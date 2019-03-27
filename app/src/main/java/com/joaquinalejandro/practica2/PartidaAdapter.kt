package com.joaquinalejandro.practica2

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import es.uam.eps.multij.Partida
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.ViewGroup
import com.joaquinalejandro.practica2.model.TableroJuego
import java.util.ArrayList

class PartidaHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    /*override fun onClick(v: View?) {
        Snackbar.make(itemView, "Item ${idTextView.text} selected",
            Snackbar.LENGTH_SHORT).show()
    }*/

    lateinit var idTextView: TextView
    lateinit var jugadoresTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var tab: TextView

    init {
        idTextView = itemView.findViewById(R.id.list_item_id) as TextView
        jugadoresTextView = itemView.findViewById(R.id.list_item_players) as TextView
        dateTextView = itemView.findViewById(R.id.list_item_date) as TextView
        tab = itemView.findViewById(R.id.tab) as TextView

    }
    fun vincularPartida(partida: PartidaLista,listener:(PartidaLista)->Unit) {
         idTextView.text = partida.idC

         jugadoresTextView.text = partida.jugadoresC
         dateTextView.text = partida.dateC.substringBefore("GMT")
         itemView.setOnClickListener({listener(partida)})
        tab.text=partida.tabC.toString()
    }
}

class PartidaAdapter(val partidas: ArrayList<PartidaLista>, val listener: (PartidaLista) -> Unit): RecyclerView.Adapter<PartidaHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartidaHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.elemento_lista_partida, parent, false)
        return PartidaHolder(view)
    }
    override fun getItemCount(): Int = partidas.size
    override fun onBindViewHolder(holder: PartidaHolder, position: Int) {
        holder.vincularPartida(partidas[position],listener)
    }
}

class PartidaLista(val idC:String, val dateC : String,var jugadoresC:String,var tablero:String, var tabC:TableroJuego)