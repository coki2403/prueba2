package com.joaquinalejandro.practica2.vistaRecicladora

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.model.TableroConecta4
import es.uam.eps.multij.Partida
import org.json.JSONObject
import java.util.*

class PartidaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /*override fun onClick(v: View?) {
        Snackbar.make(itemView, "Item ${idTextView.text} selected",
            Snackbar.LENGTH_SHORT).show()
    }*/

    var idTextView: TextView
    var jugadoresTextView: TextView
    var dateTextView: TextView
    /*var tab: TextView*/
    var tabImprimir: TextView

    init {
        idTextView = itemView.findViewById(R.id.list_item_id) as TextView
        jugadoresTextView = itemView.findViewById(R.id.list_item_players) as TextView
        dateTextView = itemView.findViewById(R.id.list_item_date) as TextView
        /*tab = itemView.findViewById(R.id.tab) as TextView*/
        tabImprimir = itemView.findViewById(R.id.tabImprimir) as TextView

    }

    fun vincularPartida(partida: PartidaLista, listener: (PartidaLista) -> Unit) {
        idTextView.text = partida.id
        val str=partida.firstPlayerName+" vs "+partida.secondPlayerName
        jugadoresTextView.text = str
        dateTextView.text = partida.date.substringBefore("GMT")
        itemView.setOnClickListener({ listener(partida) })
        /*tab.text = partida.board.toString()*/
        tabImprimir.text = partida.tabImprimir.toString()
    }
}

class PartidaAdapter(var partidas: List<PartidaLista>, val listener: (PartidaLista) -> Unit) :
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

class PartidaLista(val fil: Int,val col: Int ){

    var id: String
    var title: String
    var date: String
    lateinit var tabImprimir: String
    lateinit var board: String
    lateinit var firstPlayerName: String
    lateinit var firstPlayerUUID: String
    lateinit var secondPlayerName: String
    lateinit var secondPlayerUUID: String


    init {
        /*idC = UUID.randomUUID().toString()
        dateC = Date().toString()
        tabC = TableroConecta4(7, 6)
        tablero = TableroConecta4(7, 6).toString()*/

        id = UUID.randomUUID().toString()
        title = "ROUND ${id.toString().substring(19, 23).toUpperCase()}"
        date = Date().toString()
    }

    fun toJSONString(): String {

        val json = JSONObject()
        json.put("id", id)
        json.put("title", title)
        json.put("date", date)
        json.put("filas", fil)
        json.put("columnas", col)
        json.put("boardString", board)
        json.put("firstPlayerName", firstPlayerName)
        json.put("firstPlayerUUID", firstPlayerUUID)
        json.put("secondPlayerName", secondPlayerName)
        json.put("secondPlayerUUID", secondPlayerUUID)
        return json.toString()
    }

    companion object {

        fun fromJSONString(string: String): PartidaLista {

            val jsonObject = JSONObject(string)
            val round = PartidaLista(jsonObject.get("filas") as Int,jsonObject.get("columnas") as Int)
            round.id = jsonObject.get("id") as String
            round.title = jsonObject.get("title") as String
            round.date = jsonObject.get("date") as String
            round.board=jsonObject.get("boardString") as String
            round.firstPlayerName = jsonObject.get("firstPlayerName") as String
            round.firstPlayerUUID = jsonObject.get("firstPlayerUUID") as String
            round.secondPlayerName = jsonObject.get("secondPlayerName") as String
            round.secondPlayerUUID = jsonObject.get("secondPlayerUUID") as String
            return round
        }
    }
}