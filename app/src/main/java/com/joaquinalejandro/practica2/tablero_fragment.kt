package com.joaquinalejandro.practica2

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getDrawable
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.joaquinalejandro.practica2.Activities.ControladorPlayer
import com.joaquinalejandro.practica2.Activities.MenuActivity
import com.joaquinalejandro.practica2.Activities.PartidaListaActivity
import com.joaquinalejandro.practica2.model.TableroJuego
import com.joaquinalejandro.practica2.R
import es.uam.eps.multij.*
import kotlinx.android.synthetic.main.fragment_tablero_fragment.*


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
class tablero_fragment : Fragment(), PartidaListener {
    // TODO: Rename and change types of parameters
    private var idCarga: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idCarga = it.getString(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ids = arrayOf(
            arrayOf(f00, f01, f02, f03, f04, f05, f06),
            arrayOf(f10, f11, f12, f13, f14, f15, f16),
            arrayOf(f20, f21, f22, f23, f24, f25, f26),
            arrayOf(f30, f31, f32, f33, f34, f35, f36),
            arrayOf(f40, f41, f42, f43, f44, f45, f46),
            arrayOf(f50, f51, f52, f53, f54, f55, f56)
        )

        Guardar.setOnClickListener(
            {guardarPartida(view)}
        )

        botonLista.setOnClickListener(
            {aPartidas(view)}
        )

        /*if (intent.extras != null){
            idPartida = intent.extras.getInt("ID")
            println("Recibido: ${intent.extras.getInt("ID")}")
        }*/

        if (idCarga != null){
            idPartida = idCarga.toString().toInt()
            println("Recibido: ${idCarga}")
        }else
            idPartida = -1

        if (idPartida == -1)
            comenzar()
        else {
            cargarPartida()
        }

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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            tablero_fragment().apply {
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
        private lateinit var partida: Partida
        private lateinit var tablero: Tablero


        override fun onCambioEnPartida(evento: Evento) {
            when (evento.tipo) {
                Evento.EVENTO_CAMBIO ->{
                    TextoInfo.text=evento.descripcion
                    actualizaInterfaz()

                }
                Evento.EVENTO_FIN -> {
                    actualizaInterfaz()
                    TextoInfo.text=evento.descripcion
                    Toast.makeText(
                        context,
                        "Fin del juego", Toast.LENGTH_SHORT
                    ).show()
                    /*********************************MAs bonito*******************************/
                }
            }
        }


        fun actualizaInterfaz() {
            if (ids != null) {
                for (i in 0 until filas)
                    for (j in 0 until columnas) {
                        val elem = (tablero as TableroJuego).getTablero(i, j)
                        if (elem == 1) {
                            ids!!.get(i)[j].setImageDrawable(getDrawable(context!!,R.drawable.circulo_rojo))
                        } else if (elem == 2) {
                            ids!!.get(i)[j].setImageDrawable(getDrawable(context!!,R.drawable.circulo_amarillo))/*****************************************CUIDADO(cotext)***********************************/
                        } else {
                            ids!!.get(i)[j].setImageDrawable(getDrawable(context!!,R.drawable.circulo))
                        }

                    }

            }

        }


        /*override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            setContentView(R.layout.activity_main)




        }*/

        fun cargarPartida() {
            val partidaString = RepositorioPartidas.getPartida(idPartida)
            val tablero = partidaString.split(",".toRegex())[2]
            val estado = partidaString.split(",".toRegex())[3]
            val turno = partidaString.split(",".toRegex())[4]
            val numjugadas = partidaString.split(",".toRegex())[5]

            /*jugadores*/
            val listaJugadores = arrayListOf<Jugador>()
            val randomPlayer = JugadorAleatorio("Random player")
            val jugadorHumano = ControladorPlayer()
            registerListeners(jugadorHumano)
            listaJugadores.add(jugadorHumano)
            listaJugadores.add(randomPlayer)

            /*tablero*/
            val t2 = TableroJuego(0, 0)
            if (turno.toInt() == 1)
                t2.cambiarTurno()
            t2.stringToTablero(tablero)
            t2.setEstado(estado.toInt())
            t2.setNumJugadas(numjugadas.toInt())
            this.tablero = t2
            partida = Partida(this.tablero, listaJugadores)
            partida.addObservador(this)
            if(partida.tablero.estado== Tablero.EN_CURSO)
                partida.comenzar()
            else
                TextoInfo.text="Partida Finalizada"
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

        }


        fun guardarPartida(v: View) {
            if (idPartida == -1) {
                idPartida = RepositorioPartidas.addPartida(partida)
            } else {
                RepositorioPartidas.actualizarPartida(idPartida, partida)
            }

            Snackbar.make(
                v, "Partida guardada como $idPartida",
                Snackbar.LENGTH_SHORT
            ).show()
            startActivity(Intent(v.context, MenuActivity::class.java))
        }

        fun comenzar() {
            val listaJugadores = ArrayList<Jugador>()
            val randomPlayer = JugadorAleatorio("Maquina")
            val jugadorHumano = ControladorPlayer()
            registerListeners(jugadorHumano)
            listaJugadores.add(jugadorHumano)
            listaJugadores.add(randomPlayer)
            tablero = TableroJuego(filas, columnas)
            partida = Partida(tablero, listaJugadores)
            partida.addObservador(this)
            jugadorHumano.setPartida(partida)
            partida.comenzar()

        }

        fun aPartidas(v: View) {

            startActivity(Intent(v.context, PartidaListaActivity::class.java))

        }
    }


