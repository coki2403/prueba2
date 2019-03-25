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
        val tableroFrag: tablero_fragment

        /*Comprobacion para cargar la aprtida*/
        if (intent.extras != null){
            val idPartida = intent.extras.getInt("ID")
             tableroFrag = tablero_fragment.newInstance(idPartida.toString())
        }else{
             tableroFrag= tablero_fragment.newInstance("-1")
        }



        if (fm.findFragmentById(R.id.fragment_container) == null) {

            fm.executeTransaction { add(R.id.fragment_container, tableroFrag) }
        }



    }

}
