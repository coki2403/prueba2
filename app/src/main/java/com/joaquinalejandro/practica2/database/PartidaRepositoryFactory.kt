package com.joaquinalejandro.practica2.database

import android.content.Context
import com.joaquinalejandro.practica2.activities.SettingsActivity
import com.joaquinalejandro.practica2.firebase.FBDataBase
import com.joaquinalejandro.practica2.vistaRecicladora.IRepositorioPartidas

object PartidaRepositoryFactory {
    private val LOCAL = true
    fun createRepository(context: Context): IRepositorioPartidas? {
        val repository: IRepositorioPartidas
        repository = if (SettingsActivity.getTipoBd(context)=="LOCAL") ERDataBase(context) else FBDataBase()
        try {
            repository.open()
        } catch (e: Exception) {
            println("EXCEPCION")
            return null
        }
        return repository
    }
}