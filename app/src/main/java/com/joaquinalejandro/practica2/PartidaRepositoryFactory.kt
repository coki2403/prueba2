package com.joaquinalejandro.practica2

import android.content.Context
import com.joaquinalejandro.practica2.database.ERDataBase
import com.joaquinalejandro.practica2.vistaRecicladora.IRepositorioPartidas

object PartidaRepositoryFactory {
    private val LOCAL = true
    fun createRepository(context: Context): IRepositorioPartidas? {
        val repository: IRepositorioPartidas
        repository = if (LOCAL) ERDataBase(context) else ERDataBase(context)
        try {
            repository.open()
        } catch (e: Exception) {
            return null
        }
        return repository
    }
}