package com.joaquinalejandro.practica2.fragmentos

import android.content.Context
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.TextView
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaAdapter
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista
import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.activities.ControladorPlayer
import com.joaquinalejandro.practica2.activities.SettingsActivity
import com.joaquinalejandro.practica2.database.PartidaRepositoryFactory
import com.joaquinalejandro.practica2.extras.update
import com.joaquinalejandro.practica2.model.TableroConecta4
import com.joaquinalejandro.practica2.model.guardarPartida
import com.joaquinalejandro.practica2.vistaRecicladora.IRepositorioPartidas
import es.uam.eps.multij.Jugador
import es.uam.eps.multij.JugadorAleatorio
import es.uam.eps.multij.Partida
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

    interface OnRoundListFragmentInteractionListener {
        fun onRoundSelected(round: Partida)
        fun onPreferenceSelected()
        fun onNewRoundAdded()
    }


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
            update("RND")
            { round -> listener?.onPartidaSelected(round) }
        }

        crear.setOnClickListener({crearPartida()})
    }

    fun crearPartida(){
        val listaJugadores = ArrayList<Jugador>()
        val randomPlayer = JugadorAleatorio("Maquina")
        val jugadorHumano = ControladorPlayer()
        listaJugadores.add(jugadorHumano)
        listaJugadores.add(randomPlayer)
        var tablero = TableroConecta4(6, 7)
        var partida = Partida(tablero, listaJugadores)
        val repository = PartidaRepositoryFactory.createRepository(this.context!!)
        val callback = object : IRepositorioPartidas.BooleanCallback {
            override fun onResponse(response: Boolean) {
                if (response == true) {
                    /*recyclerView.update(
                        "Random",
                        { partida -> onPartidaSelected(partida) }
                    )*/
                } else
                    println("error")
            }
        }
        val partidaLista=PartidaLista(6,7)
        partidaLista.board=partida.guardarPartida()
        partidaLista.firstPlayerName= SettingsActivity.getPlayerName(context!!)
        partidaLista.firstPlayerUUID= SettingsActivity.getPlayerUUID(context!!)

        partidaLista.secondPlayerName="maquina"
        partidaLista.secondPlayerUUID="maquina UUID"
        repository?.addPartida(partidaLista, callback)
        updateUI()
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
        fun onPreferenceSelected()
        fun onNewRoundAdded()
    }

    /*override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.botonprueba -> {
                listener?.onNewRoundAdded()
                /*partida_recycler_view.onActualizaLista { partida:PartidaLista -> listener?.onPartidaSelected(partida) }*/
                return true
            }
            R.id.botonprueba2 -> {
                listener?.onPreferenceSelected()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return true

    }*/



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
        recyclerView.update("RND")
        { round -> listener?.onPartidaSelected(round) }
        /*recyclerView.apply {
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
        if (RepositorioPartidas.partidas.size == 0)
            sin_partidas.visibility = TextView.VISIBLE
        else
            sin_partidas.visibility = TextView.INVISIBLE*/
    }
}
