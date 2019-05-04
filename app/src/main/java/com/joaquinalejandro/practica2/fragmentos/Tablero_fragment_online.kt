package com.joaquinalejandro.practica2.fragmentos

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.activities.ControladorPlayer
import com.joaquinalejandro.practica2.activities.MenuActivity
import com.joaquinalejandro.practica2.activities.PartidaListaActivity
import com.joaquinalejandro.practica2.model.TableroConecta4
import es.uam.eps.multij.*
import kotlinx.android.synthetic.main.fragment_tablero_fragment.*
import android.support.v7.app.AlertDialog
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.joaquinalejandro.practica2.activities.SettingsActivity
import com.joaquinalejandro.practica2.database.PartidaRepositoryFactory
import com.joaquinalejandro.practica2.firebase.FBDataBase
import com.joaquinalejandro.practica2.model.JugadorRemoto
import com.joaquinalejandro.practica2.model.guardarPartida
import com.joaquinalejandro.practica2.vistaRecicladora.IRepositorioPartidas
import com.joaquinalejandro.practica2.vistaRecicladora.PartidaLista
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [tablero_fragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [tablero_fragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class tablero_fragment_online : Fragment(), PartidaListener {
    // TODO: Rename and change types of parameters
    private var idCarga: String? = null
    var listener: OnTableroFragmentInteractionListener? = null
    lateinit var partidaLista: PartidaLista


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is tablero_fragment_online.OnTableroFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(
                context.toString() +
                        " must implement OnTableroFragmentInteractionListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("idPartida", idPartida)
        println("SAVE INSTANCE")
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        reinicio.visibility=View.INVISIBLE
        try {
            if (savedInstanceState?.getInt("idPartida") != null)
                idPartida = savedInstanceState.getInt("idPartida")
            else
                idPartida = -1
        } catch (e: ExcepcionJuego) {
            e.printStackTrace()
        }


        println("RESTORE INSTANCE $idPartida")
        ids = arrayOf(
            arrayOf(f00, f01, f02, f03, f04, f05, f06),
            arrayOf(f10, f11, f12, f13, f14, f15, f16),
            arrayOf(f20, f21, f22, f23, f24, f25, f26),
            arrayOf(f30, f31, f32, f33, f34, f35, f36),
            arrayOf(f40, f41, f42, f43, f44, f45, f46),
            arrayOf(f50, f51, f52, f53, f54, f55, f56)
        )


        botonLista.setOnClickListener(
            { aPartidas(view!!) }
        )

        /*if (intent.extras != null){
            idPartida = intent.extras.getInt("ID")
            println("Recibido: ${intent.extras.getInt("ID")}")
        }*/

        if (idPartida == -1) {
            if (idCarga != null) {
                idPartida = 0
                if(idCarga=="-1"){
                    idPartida = -1
                }else{
                    partidaLista= PartidaLista.fromJSONString(idCarga.toString())
                    println("Recibido: ${idCarga}")
                }

            } else
                idPartida = -1
        }


        if (idPartida == -1) {
            comenzar()
            Toast.makeText(
                context,
                "Comenzada nueva partida", Toast.LENGTH_SHORT
            ).show()
        } else {
            cargarPartida()
        }

        if (idPartida == -1)
            titulo.text = "Nueva Partida"
        else
            titulo.text = "Partida " + idPartida

    }

    interface OnTableroFragmentInteractionListener {
        fun onReiniciar()
        fun onActualizaLista(partidaLista: PartidaLista)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCarga = it.getString(ARG_PARAM1)
            if(idCarga!="-1")
              partidaLista = PartidaLista.fromJSONString(it.getString(ARG_PARAM1))
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //partida_id.text = "${partida.id}"
        //if (savedInstanceState != null) {
            //partida.Tablero.stringToTablero(savedInstanceState.getString(BOARDSTRING))
        //}

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tablero_fragment, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment tablero_fragment.
         */

        @JvmStatic
        fun newInstance(param1: String) =
            tablero_fragment_online().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    /*  Aqui empieza la actividad del tablero */

    var ids: Array<Array<ImageView>>? = null
    var filas = 6
    var columnas = 7
    var idPartida = -1
    var descripcion:String=""
    private lateinit var partida: Partida
    private lateinit var tablero: Tablero


    override fun onCambioEnPartida(evento: Evento) {

        when (evento.tipo) {
            Evento.EVENTO_CAMBIO -> {
                TextoInfo.text = evento.descripcion
                SettingsActivity.setDescripcion(context!!, evento.descripcion)
                actualizaInterfaz()
                descripcion=evento.descripcion
                guardarPartida()


            }
            Evento.EVENTO_FIN -> {
                actualizaInterfaz()
                guardarPartida()
                TextoInfo.text = evento.descripcion
                createDialog(evento.descripcion).show()
                descripcion=evento.descripcion
                guardarPartida()
            }
        }
    }


    fun actualizaInterfaz() {
        if (ids == null)
            return
        for (i in 0 until filas) {
            for (j in 0 until columnas) {
                val elem = (tablero as TableroConecta4).getTablero(i, j)
                if (elem == 1) {
                    ids!!.get(i)[j].setImageDrawable(
                        getDrawable(
                            context!!,
                            R.drawable.circulo_rojo
                        )
                    )
                } else if (elem == 2) {
                    ids!!.get(i)[j].setImageDrawable(
                        getDrawable(
                            context!!,
                            R.drawable.circulo_amarillo
                        )
                    )
                    /*****************************************CUIDADO(cotext)***********************************/
                } else {
                    ids!!.get(i)[j].setImageDrawable(
                        getDrawable(
                            context!!,
                            R.drawable.circulo
                        )
                    )
                }
            }
        }
    }



    /***** volver al menus***/
    /*override  fun onBackPressed(){

    }*/

    fun cargarPartida() {
        val partidaString = partidaLista.board
        val tablero = partidaString.split(",".toRegex())[2]
        val estado = partidaString.split(",".toRegex())[3]
        val turno = partidaString.split(",".toRegex())[4]
        val numjugadas = partidaString.split(",".toRegex())[5]

        val listaJugadores = arrayListOf<Jugador>()
        val jugadorHumano:Jugador
        /*jugadores*/
        if(partidaLista.secondPlayerName=="" && partidaLista.firstPlayerName!=SettingsActivity.getPlayerName(context!!)){/*A la partida le falta un jugador*/
            val jugadorRemoto = JugadorRemoto("")
            jugadorHumano = ControladorPlayer(SettingsActivity.getPlayerUUID(context!!))
            registerListeners(jugadorHumano)
            listaJugadores.add(jugadorRemoto)
            listaJugadores.add(jugadorHumano)
            partidaLista.secondPlayerName=SettingsActivity.getPlayerName(context!!)
            partidaLista.secondPlayerUUID=SettingsActivity.getPlayerUUID(context!!)
            //partidaLista.descripcion = SettingsActivity.getDescripcion(context!!)
            SettingsActivity.setTurno(context!!,1)

        }else{
            if(partidaLista.firstPlayerName==SettingsActivity.getPlayerName(context!!)){
                val jugadorRemoto = JugadorRemoto("")
                jugadorHumano = ControladorPlayer(SettingsActivity.getPlayerUUID(context!!))
                registerListeners(jugadorHumano)
                listaJugadores.add(jugadorHumano)
                listaJugadores.add(jugadorRemoto)
                //partidaLista.descripcion = SettingsActivity.getDescripcion(context!!)
                SettingsActivity.setTurno(context!!,0)

            }else if(partidaLista.secondPlayerName==SettingsActivity.getPlayerName(context!!)){
                val jugadorRemoto = JugadorRemoto("")
                jugadorHumano = ControladorPlayer(SettingsActivity.getPlayerUUID(context!!))
                registerListeners(jugadorHumano)
                listaJugadores.add(jugadorRemoto)
                listaJugadores.add(jugadorHumano)
                //partidaLista.descripcion = SettingsActivity.getDescripcion(context!!)
                SettingsActivity.setTurno(context!!,1)
            }else{
                println("Error al cargar partida")
                return
            }

        }




        /*tablero*/
        val t2 = TableroConecta4(0, 0)
        if (turno.toInt() == 1)
            t2.cambiarTurno()
        t2.stringToTablero(tablero)
        t2.setEstado(estado.toInt())
        t2.setNumJugadas(numjugadas.toInt())
        //t2.numJugadas = numjugadas.toInt()
        //t2.estado = estado.toInt()
        this.tablero = t2
        partida = Partida(this.tablero, listaJugadores)
        partida.addObservador(this)
        if (partida.tablero.estado == Tablero.EN_CURSO)
            partida.comenzar()
        else
            TextoInfo.text = "Partida Finalizada"
        jugadorHumano.setPartida(partida)
        Toast.makeText(
            context,
            "Cargada Partida con id $idPartida", Toast.LENGTH_SHORT
        ).show()
        actualizaInterfaz()
    }

    fun registerListeners(jugador: ControladorPlayer) {
        col1?.setOnClickListener(jugador)
        col2?.setOnClickListener(jugador)
        col3?.setOnClickListener(jugador)
        col4?.setOnClickListener(jugador)
        col5?.setOnClickListener(jugador)
        col6?.setOnClickListener(jugador)
        col7?.setOnClickListener(jugador)
        reinicio.setOnClickListener({ listener?.onReiniciar() })


    }


    fun guardarPartida() {
        if (idPartida == -1) {
            partidaLista= PartidaLista(filas,columnas)
            partidaLista.board=partida.guardarPartida()

            partidaLista.firstPlayerName=partida.getJugador(0).nombre
            partidaLista.firstPlayerUUID=SettingsActivity.getPlayerUUID(context!!)

            partidaLista.secondPlayerName=partida.getJugador(1).nombre
            partidaLista.secondPlayerUUID=""
            partidaLista.descripcion=descripcion

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
            repository?.addPartida(partidaLista, callback)
            idPartida=0
        }
        partidaLista.board=partida.guardarPartida()
        partidaLista.descripcion=descripcion
        listener?.onActualizaLista(partidaLista)
        titulo.text = partidaLista.title
        //startActivity(Intent(v.context, MenuActivity::class.java))
        compruebaCambio()

    }

    fun comenzar() {
        val listaJugadores = ArrayList<Jugador>()
        val jugadorRemoto = JugadorRemoto("")
        val jugadorHumano = ControladorPlayer(SettingsActivity.getPlayerUUID(context!!))
        registerListeners(jugadorHumano)
        listaJugadores.add(jugadorHumano)
        listaJugadores.add(jugadorRemoto)
        tablero = TableroConecta4(filas, columnas)
        partida = Partida(tablero, listaJugadores)
        partida.addObservador(this)
        jugadorHumano.setPartida(partida)
        SettingsActivity.setTurno(context!!,0)
        partida.comenzar()

    }

    fun aPartidas(v: View) {

        startActivity(Intent(v.context, PartidaListaActivity::class.java))

    }


    fun createDialog(msg: String): AlertDialog {
        val builder = AlertDialog.Builder(this.getActivity()!!)
        builder.setTitle(R.string.tiutlo_dialogo)
            .setMessage("¿Comenzar nueva partida?")
            .setPositiveButton(R.string.aceptar_dialogo)
            { dialog, _->
                listener?.onReiniciar()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancelar_dialogo)
            { dialog, _ ->
                dialog.dismiss()
            }

        return builder.create()
    }

    fun compruebaCambio(){
        val repository = PartidaRepositoryFactory.createRepository(this.context!!)
        val callback = object : IRepositorioPartidas.RoundsCallback {
            override fun onResponse(rounds: List<PartidaLista>) {
                for(round in rounds){
                    if(round.id==partidaLista.id){
                        partidaLista=round
                        val partidaString = partidaLista.board
                        val tab = partidaString.split(",".toRegex())[2]
                        val estado = partidaString.split(",".toRegex())[3]
                        val turno = partidaString.split(",".toRegex())[4]
                        val numjugadas = partidaString.split(",".toRegex())[5]
                        /*tablero*/
                        val t2 = TableroConecta4(0, 0)

                        if(t2.turno!=turno.toInt())
                            t2.cambiarTurno()

                        t2.stringToTablero(tab)

                        /*t2.setEstado(estado.toInt())
                        t2.setNumJugadas(numjugadas.toInt())*/
                        t2.numJugadas = numjugadas.toInt()
                        t2.estado = estado.toInt()
                        tablero = t2

                        (partida.tablero as TableroConecta4).tablero=t2.tablero
                        (partida.tablero as TableroConecta4).estado=t2.estado
                        (partida.tablero as TableroConecta4).numJugadas=t2.numJugadas
                        if(partida.tablero.turno!=t2.turno)
                            (partida.tablero as TableroConecta4).cambiarTurno()
                        if(context!=null){
                            actualizaInterfaz()
                            TextoInfo.text = partidaLista.descripcion

                            if(turno == SettingsActivity.getTurno(context!!).toString()) {

                                val toast = Toast.makeText(context, "Tu turno!", Toast.LENGTH_LONG)
                                val view = toast.view
                                view.background.setColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN)

                                val text = view.findViewById<TextView>(android.R.id.message)
                                text.setTextColor(Color.WHITE)
                                toast.show()
                            }

                        }

                    }
                }
            }

            override fun onError(error: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

        (repository as FBDataBase).startListeningChanges(callback)
    }

}


