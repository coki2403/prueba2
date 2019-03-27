package com.joaquinalejandro.practica2.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.joaquinalejandro.practica2.R
import kotlinx.android.synthetic.main.activity_menu.*
import java.lang.System.exit

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        nuevapartida.setOnClickListener({ startActivity(Intent(this@MenuActivity, MainActivity::class.java)) })
        cargarpartida.setOnClickListener({ startActivity(Intent(this@MenuActivity, PartidaListaActivity::class.java)) })

    }

}
