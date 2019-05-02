package com.joaquinalejandro.practica2.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.CompoundButton
import com.joaquinalejandro.practica2.R
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        nuevapartida.setOnClickListener({ startActivity(Intent(this@MenuActivity, MainActivity::class.java)) })
        cargarpartida.setOnClickListener({ startActivity(Intent(this@MenuActivity, PartidaListaActivity::class.java)) })
        ajustes.setOnClickListener({ startActivity(Intent(this@MenuActivity, SettingsActivity::class.java)) })
        setSupportActionBar(my_toolbar)
        online_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                SettingsActivity.setTipoBD(this,"FIREBASE")
                println("firebase")
            } else {
                SettingsActivity.setTipoBD(this,"LOCAL")
                println("local")
            }
        }
        if(SettingsActivity.getTipoBd(this)=="LOCAL"){
            online_switch.isChecked=false
        }else
            online_switch.isChecked=true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater?.inflate(R.menu.menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.login_menu -> {
                startActivity(Intent(this@MenuActivity, LoginActivity::class.java))
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

}
