package com.joaquinalejandro.practica2.model

import JugadorJuego
import es.uam.eps.multij.Jugador
import es.uam.eps.multij.JugadorAleatorio
import es.uam.eps.multij.Partida
import java.io.File

fun Partida.cargarPartida():Partida{
    val nueva:Partida
    val f = File("Partida.txt")
    val datosPartida = f.readLines() //aqui estan todos
    val nombre1 = datosPartida[0].split(",".toRegex())[0]
    val nombre2 = datosPartida[0].split(",".toRegex())[1]
    val tablero = datosPartida[0].split(",".toRegex())[2]
    val estado = datosPartida[0].split(",".toRegex())[3]
    val turno = datosPartida[0].split(",".toRegex())[4]
    val numjugadas = datosPartida[0].split(",".toRegex())[5]
    val listaJugadores = arrayListOf<Jugador>()

    listaJugadores.add(JugadorJuego(nombre1))
    if(nombre2 == "maquina") {
        println("Tipo de partida: Jugador vs Maquina")
        listaJugadores.add(JugadorAleatorio("maquina"))
    }else{
        println("Tipo de partida: Jugador vs Jugador")
        listaJugadores.add(JugadorJuego(nombre2))
    }
    val t2= TableroJuego(0, 0)
    if(turno.toInt()==1)
        t2.cambiarTurno()
    t2.stringToTablero(tablero)
    t2.setEstado(estado.toInt())
    t2.setNumJugadas(numjugadas.toInt())
    nueva=Partida(t2,listaJugadores)
    nueva.addObservador(ObservadorJuego())

    return nueva

}


fun Partida.guardarPartida():String {
    //val path = context.getFilesDir()
    //val letDirectory = File(path, "LET") letDirectory.mkdirs()
    //val file = File(letDirectory , "partida.txt")
    //file.appendText("hola que tal")


    //f.appendText("Hola")
    val str="${getJugador(0)?.nombre},${getJugador(1)?.nombre},${tablero?.tableroToString()},${tablero?.estado},${tablero?.turno},${tablero?.numJugadas}"
    return str


}