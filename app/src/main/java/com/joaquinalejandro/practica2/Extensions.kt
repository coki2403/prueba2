package com.joaquinalejandro.practica2

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction


fun FragmentManager.executeTransaction(operations: (FragmentTransaction.() -> Unit)) {
    val transaction = beginTransaction()
    transaction.operations()
    transaction.commit()
}