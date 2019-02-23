package com.joaquinalejandro.practica2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        f00.setImageDrawable(getDrawable(R.drawable.circulo_amarillo))
        f45.setImageDrawable(getDrawable(R.drawable.circulo_rojo))
    }
}
