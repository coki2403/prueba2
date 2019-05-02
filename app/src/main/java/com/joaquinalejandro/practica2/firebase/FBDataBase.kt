package com.joaquinalejandro.practica2.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.joaquinalejandro.practica2.vistaRecicladora.IRepositorioPartidas
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista
import org.json.JSONObject

class FBDataBase : IRepositorioPartidas{

    private val DATABASENAME = "partidas"
    lateinit var db: DatabaseReference

    override fun open() {

        db = FirebaseDatabase.getInstance().reference.child(DATABASENAME)

    }

    override fun close() {

    }

    override fun login(playername: String, password: String, callback:
    IRepositorioPartidas.LoginRegisterCallback) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(playername, password).addOnCompleteListener()
        {task->
            // Aquí deberás colocar el código para llamar a onLogin() y onError()
            if (task.isSuccessful) {
                callback.onLogin(playername)
            }else{
                callback.onError("Error al Registrar sesión.")
            }
        }
    }


    override fun register(playername: String, password: String, callback: IRepositorioPartidas.LoginRegisterCallback) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(playername,password).addOnCompleteListener()
        {task->
            // Aquí deberás colocar el código para llamar a onLogin() y onError()
            if (task.isSuccessful) {
                callback.onLogin(playername)
            }else{
                callback.onError("Error al Registrar sesión.")
            }


        }

    }

    override fun getPartidas(playeruuid: String,
                           orderByField: String,
                           group: String,
                           callback: IRepositorioPartidas.RoundsCallback) {
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("DEBUG", p0.toString())
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var partidas = listOf<PartidaLista>()
                for (postSnapshot in dataSnapshot.children) {

                    val round = postSnapshot.getValue(PartidaLista::class.java)!!
                    //if (isOpenOrIamIn(round))
                        partidas += round
                }
                callback.onResponse(partidas)
            }
        })
    }


    override fun addPartida(round: PartidaLista, callback: IRepositorioPartidas.BooleanCallback) {

        if (db.child(round.id).setValue(round).isSuccessful)
            callback.onResponse(true)
        else
            callback.onResponse(false)
    }


    override fun actualizarPartida(round: PartidaLista, callback: IRepositorioPartidas.BooleanCallback) {
        if (db.child(round.id).setValue(round).isSuccessful)
            callback.onResponse(true)
        else
            callback.onResponse(false)
    }

    fun startListeningChanges(callback: IRepositorioPartidas.RoundsCallback) {
        db = FirebaseDatabase.getInstance().getReference().child(DATABASENAME)
        db.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("DEBUG", p0.toString())
            }
            override fun onDataChange(p0: DataSnapshot) {
                var partidas = listOf<PartidaLista>()
                for (postSnapshot in p0.children)
                    partidas += postSnapshot.getValue(PartidaLista::class.java)!!
                callback.onResponse(partidas)
            }
        })
    }

    fun startListeningBoardChanges(callback: IRepositorioPartidas.RoundsCallback) {  }
}