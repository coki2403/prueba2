package com.joaquinalejandro.practica2

import android.graphics.Color
import android.graphics.Paint
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.joaquinalejandro.practica2.model.TableroJuego


fun FragmentManager.executeTransaction(operations: (FragmentTransaction.() -> Unit)) {
    val transaction = beginTransaction()
    transaction.operations()
    transaction.commit()
}

fun Paint.setColor(board: TableroJuego, i: Int, j: Int) {
    if (board.getTablero(i, j) == 1)
        setColor(Color.parseColor("#00574B"))
    else if (board.getTablero(i, j) == 0)
        setColor(Color.GRAY)
    else
    setColor(Color.parseColor("#D81B60"))
}