package com.joaquinalejandro.practica2.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaAdapter
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista
import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.vistaRecicladora.RepositorioPartidas
import com.joaquinalejandro.practica2.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_lista_fragment.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [lista_fragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [lista_fragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class lista_fragment : Fragment() {

    var listener: OnPartidaListaFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnPartidaListaFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(
                context.toString() +
                        " must implement OnPartidaListaFragmentInteractionListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnPartidaListaFragmentInteractionListener {
        fun onPartidaSelected(partida: PartidaLista)
    }


    /*fun onPartidaSelected(partida: PartidaLista) {


        val intent = Intent(activity, MainActivity::class.java)
        println("sel: ${partida.idC}")
        intent.putExtra("ID", partida.idC.toInt())
        println("enviado: ${intent.extras.getInt("ID")}")
        startActivity(intent)


    }*/

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    fun updateUI() {
        recyclerView.apply {
            if (adapter == null)
                adapter =
                    PartidaAdapter(RepositorioPartidas.partidas) { partida ->
                        listener?.onPartidaSelected(
                            partida
                        )
                    }
            else
                adapter.notifyDataSetChanged()
        }
    }
}
