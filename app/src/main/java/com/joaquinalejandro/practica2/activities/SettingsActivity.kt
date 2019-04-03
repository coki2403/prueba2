package com.joaquinalejandro.practica2.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.joaquinalejandro.practica2.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    companion object {

        val TABLERO_COLUMNAS_KEY = "num_filas"
        val TABLERO_COLUMNAS_DEFAULT = "7"

        val TABLERO_FILAS_KEY = "num_columnas"
        val TABLERO_FILAS_DEFAULT = "6"

        fun getTableroColumnas(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(TABLERO_COLUMNAS_KEY, TABLERO_COLUMNAS_DEFAULT)
        }

        fun getTableroFilas(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(TABLERO_FILAS_KEY, TABLERO_FILAS_DEFAULT)
        }


        fun setTableroColumnas(context: Context, size: Int) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putInt(SettingsActivity.TABLERO_COLUMNAS_KEY, size)
            editor.commit()
        }

        fun setTableroFilas(context: Context, size: Int) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putInt(SettingsActivity.TABLERO_FILAS_KEY, size)
            editor.commit()
        }
    }
}
