package com.joaquinalejandro.practica2.vistaRecicladora

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.model.TableroConecta4
import org.json.JSONObject
import java.util.*

class PartidaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    fun vincularPartida(partida: PartidaLista, listener: (PartidaLista) -> Unit) {
        idTextView.text = partida.idC

        jugadoresTextView.text = partida.jugadoresC
        dateTextView.text = partida.dateC.substringBefore("GMT")
        itemView.setOnClickListener({ listener(partida) })
        tab.text = partida.tabC.toString()
    }
}

class PartidaAdapter(val partidas: ArrayList<PartidaLista>, val listener: (PartidaLista) -> Unit) :
    RecyclerView.Adapter<PartidaHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartidaHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.elemento_lista_partida, parent, false)
        return PartidaHolder(view)
    }

    override fun getItemCount(): Int = partidas.size
    override fun onBindViewHolder(holder: PartidaHolder, position: Int) {
        holder.vincularPartida(partidas[position], listener)
    }
}

class PartidaLista(
    var idC: String,
    var dateC: String,
    var jugadoresC: String,
    var tablero: String,
    var tabC: TableroConecta4,
    var firstPlayerName:String,
    var secondPlayerName:String,
    var firstPlayerUUID:String,
    var secondPlayerUUID:String



){

    init {
        idC = UUID.randomUUID().toString()
        dateC = Date().toString()
        tabC = TableroConecta4(7,6)
        tablero = TableroConecta4(7,6).toString()
    }

    fun toJSONString(): String {

        val json = JSONObject()
        json.put("id", idC)
        json.put("jugadoresC", jugadoresC)
        json.put("dateC", dateC)
        json.put("tablero", tablero)
        json.put("tabC", tabC)
        json.put("firstPlayerName", firstPlayerName)
        json.put("firstPlayerUUID", firstPlayerUUID)
        json.put("secondPlayerName", secondPlayerName)
        json.put("secondPlayerUUID", secondPlayerUUID)
        return json.toString()
    }

    companion object {

        fun fromJSONString(string: String): PartidaLista {
            val jsonObject = JSONObject(string)
            val partida = PartidaLista(
            idC = jsonObject.get("id") as String,
            jugadoresC = jsonObject.get("jugadoresC") as String,
            dateC = jsonObject.get("dateC") as String,
            tablero = jsonObject.get("tablero") as String,
                tabC = jsonObject.get("tabC") as TableroConecta4,
                firstPlayerName = jsonObject.get("firstPlayerName") as String,
            firstPlayerUUID = jsonObject.get("firstPlayerUUID") as String,
            secondPlayerName = jsonObject.get("secondPlayerName") as String,
            secondPlayerUUID = jsonObject.get("secondPlayerUUID") as String)

            return partida
        }
    }
}