package com.joaquinalejandro.practica2.Activities


import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.executeTransaction
import com.joaquinalejandro.practica2.tablero_fragment


class MainActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager
        println("HOLA")
        if (fm.findFragmentById(R.id.fragment_container) == null) {
            fm.executeTransaction { add(R.id.fragment_container, tablero_fragment()) }
        }



    }

}
