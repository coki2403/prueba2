package com.joaquinalejandro.practica2.Activities


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.joaquinalejandro.practica2.model.TableroJuego
import es.uam.eps.multij.*
import com.joaquinalejandro.practica2.R
import com.joaquinalejandro.practica2.RepositorioPartidas
import kotlinx.android.synthetic.main.activity_partida_lista.*


class MainActivity : AppCompatActivity(), PartidaListener {

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
                    getApplicationContext(),
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
                        ids!!.get(i)[j].setImageDrawable(getDrawable(R.drawable.circulo_rojo))
                    } else if (elem == 2) {
                        ids!!.get(i)[j].setImageDrawable(getDrawable(R.drawable.circulo_amarillo))
                    } else {
                        ids!!.get(i)[j].setImageDrawable(getDrawable(R.drawable.circulo))
                    }

                }

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ids = arrayOf(
            arrayOf(f00, f01, f02, f03, f04, f05, f06),
            arrayOf(f10, f11, f12, f13, f14, f15, f16),
            arrayOf(f20, f21, f22, f23, f24, f25, f26),
            arrayOf(f30, f31, f32, f33, f34, f35, f36),
            arrayOf(f40, f41, f42, f43, f44, f45, f46),
            arrayOf(f50, f51, f52, f53, f54, f55, f56)
        )
        if (intent.extras != null){
            idPartida = intent.extras.getInt("ID")
            println("Recibido: ${intent.extras.getInt("ID")}")
        }


        else
            idPartida = -1

        if (idPartida == -1)
            comenzar()
        else {
            cargarPartida()
        }


    }

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

        jugadorHumano.setPartida(partida)
        Toast.makeText(
            getApplicationContext(),
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
