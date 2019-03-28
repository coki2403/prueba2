package com.joaquinalejandro.practica2.extras

import android.graphics.Color
import android.graphics.Paint
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.joaquinalejandro.practica2.model.TableroConecta4


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