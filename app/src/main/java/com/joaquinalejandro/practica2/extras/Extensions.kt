package com.joaquinalejandro.practica2.extras

import android.graphics.Color
import android.graphics.Paint
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import com.joaquinalejandro.practica2.activities.SettingsActivity
import com.joaquinalejandro.practica2.database.PartidaRepositoryFactory
import com.joaquinalejandro.practica2.model.TableroConecta4
import com.joaquinalejandro.practica2.vistaRecicladora.IRepositorioPartidas
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaAdapter
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista
import es.uam.eps.multij.Partida


fun FragmentManager.executeTransaction(operations: (FragmentTransaction.() -> Unit)) {
    val transaction = beginTransaction()
    transaction.operations()
    transaction.commit()
}

fun Paint.setColor(board: TableroConecta4, i: Int, j: Int) {
    if (board.getTablero(i, j) == 1)
        setColor(Color.parseColor("#00574B"))
    else if (board.getTablero(i, j) == 0)
        setColor(Color.GRAY)
    else
        setColor(Color.parseColor("#D81B60"))
}


fun RecyclerView.update(userName: String, onClickListener: (PartidaLista) -> Unit) {
    val repository = PartidaRepositoryFactory.createRepository(context)
    val roundsCallback = object : IRepositorioPartidas.RoundsCallback {
        override fun onResponse(partidas: List<PartidaLista>) {
            if (adapter == null)
                adapter = PartidaAdapter(partidas, onClickListener)
            else {
                (adapter as PartidaAdapter).partidas = partidas
                adapter.notifyDataSetChanged()
            }
        }
        override fun onError(error: String) {
        }
    }
    repository?.getPartidas(SettingsActivity.getPlayerUUID(context), "", "", roundsCallback)
}




