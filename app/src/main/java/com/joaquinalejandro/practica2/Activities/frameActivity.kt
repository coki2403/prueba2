package com.joaquinalejandro.practica2.Activities

import android.app.FragmentManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast
import com.joaquinalejandro.practica2.BlankFragment
import com.joaquinalejandro.practica2.R

import kotlinx.android.synthetic.main.activity_frame.*

class frameActivity : AppCompatActivity() , BlankFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(cadena: String) {

        Toast.makeText(this, cadena, Toast.LENGTH_LONG)
        supportFragmentManager.beginTransaction().add(R.id.segundoFragment, BlankFragment()).commit()

        // if(detail.fragment_container != null){
        //abro cosa tablet
        //else{
        //cosa no tablet
        //}
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //val fm:FragmentManager = supportFragmentManager
        //val transaction = fm.beginTransaction()
        //transaction.add(R.id.fragment_container,BlankFragment()).commit()
        supportFragmentManager.beginTransaction().add(R.id.primer_fragment,BlankFragment()).commit()
    }

}
