package com.joaquinalejandro.practica2.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.Toast
import com.joaquinalejandro.practica2.R
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        nuevapartida.setOnClickListener({nuevaPartida() })
        cargarpartida.setOnClickListener({ cargarPartida() })
        ajustes.setOnClickListener({ startActivity(Intent(this@MenuActivity, SettingsActivity::class.java)) })
        setSupportActionBar(my_toolbar)

        if(SettingsActivity.getTipoBd(this)=="LOCAL"){
            tituloOnline.text="(LOCAL)"
        }else{
            tituloOnline.text="(ONLINE)"
        }
        if(SettingsActivity.getPlayerUUID(this)=="" || SettingsActivity.getPlayerName(this)=="")
            startActivity(Intent(this@MenuActivity, LoginActivity::class.java))

    }

    fun nuevaPartida(){
        if(SettingsActivity.getPlayerName(this)=="" ||SettingsActivity.getPlayerUUID(this)=="")
            Toast.makeText(
                this,
                "Se debe iniciar Sesion para poder jugar", Toast.LENGTH_SHORT
            ).show()
        else
            startActivity(Intent(this@MenuActivity, MainActivity::class.java))
    }

    fun cargarPartida(){
        if(SettingsActivity.getPlayerName(this)=="" ||SettingsActivity.getPlayerUUID(this)=="")
            Toast.makeText(
                this,
                "Se debe iniciar Sesion para poder jugar", Toast.LENGTH_SHORT
            ).show()
        else
            startActivity(Intent(this@MenuActivity, PartidaListaActivity::class.java))
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
