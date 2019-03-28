package com.joaquinalejandro.practica2.model


import es.uam.eps.multij.Partida


fun Partida.guardarPartida(): String {
    //val path = context.getFilesDir()
    //val letDirectory = File(path, "LET") letDirectory.mkdirs()
    //val file = File(letDirectory , "partida.txt")
    //file.appendText("hola que tal")


    //f.appendText("Hola")
    val str =
        "${getJugador(0)?.nombre},${getJugador(1)?.nombre},${tablero?.tableroToString()},${tablero?.estado},${tablero?.turno},${tablero?.numJugadas}"
    return str


}