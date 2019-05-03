package com.joaquinalejandro.practica2.model

import es.uam.eps.multij.ExcepcionJuego
import es.uam.eps.multij.Movimiento
import es.uam.eps.multij.Tablero
import java.util.ArrayList

class TableroConecta4(var filas: Int, var columnas: Int) : Tablero() {

    var tablero = ArrayList<ArrayList<Int>>()

    fun getTablero(fil: Int, col: Int): Int {
        return tablero[fil][col]
    }


    init {

        for (i in 0 until filas) {
            tablero.add(ArrayList())
            for (j in 0 until columnas) {
                tablero[i].add(0)
            }
        }

        estado = EN_CURSO
    }

    fun cambiarTurno() {
        cambiaTurno()
    }

    fun setEstado(est: Int) {
        estado = est
    }

    fun setNumJugadas(n: Int) {
        numJugadas = n
    }

    override fun toString(): String {
        var stringTablero = ""

        for (i in 0 until filas) {

            stringTablero += "   "

            for (j in 0 until columnas) {

                if (tablero[i][j].toString() == "0") {
                    stringTablero += " - "
                } else if (tablero[i][j].toString() == "1") {
                    stringTablero += " x "
                } else if (tablero[i][j].toString() == "2") {
                    stringTablero += " o "
                } else {
                    stringTablero += " ${tablero[i][j].toString()} ║"
                }

            }
            stringTablero += "   \n"
        }

        return stringTablero
    }

    override fun movimientosValidos(): ArrayList<Movimiento> {
        val movValidos = arrayListOf<Movimiento>()
        for (i in 0 until columnas) {
            if (tablero[0][i] == 0)
                movValidos.add(MovimientoConecta4(i))
        }
        return movValidos
    }


    override fun mueve(m: Movimiento?) {
        val pos = m?.toString()?.toInt()!!
        var posOcupada = 0
        var fil = 0
        var col = 0
        if (esValido(m)) {

            for (i in 0 until filas) {
                if (tablero[i][pos] != 0) {
                    tablero[i - 1][pos] = turno + 1
                    posOcupada = 1
                    fil = i - 1
                    col = pos
                    break
                }
            }
            if (posOcupada == 0) {
                tablero[filas - 1][pos] = turno + 1
                fil = filas - 1
                col = pos
            }
            ultimoMovimiento = MovimientoConecta4(m.toString().toInt() + 1)

            //println("FICHA COLOCADA EN: [$fil][$col]")
            actualizarEstado(fil, col)

        } else {
            throw ExcepcionJuego("Movimiento Invalido")
        }
    }

    private fun actualizarEstado(fil: Int, col: Int){

        if (movGanador(fil, col))
            estado = FINALIZADA
        else if (terminarTablas()) {
            estado = TABLAS
        } else {
            cambiaTurno()
        }
    }

    private fun movGanador(f: Int, c: Int): Boolean {
        val ficha = tablero[f][c].toString()

        return comprobarDiagonalIzquierda(f,c, ficha) or
                comprobarDiagonalDerecha(f,c, ficha)  or
                comprobarHorizontal(f,c, ficha)       or
                comprobarVertical(f,c, ficha)
    }

    fun comprobarDiagonalIzquierda(f:Int, c:Int, ficha:String) : Boolean{

        var cont = 0
        var posFil = f
        var posCol = c
        while (posFil < filas && posCol >= 0) { // primero hacia abajo
            if (tablero[posFil][posCol].toString().equals(ficha)) {
                cont++
            } else {

                break
            }

            posFil++
            posCol--
        }

        posFil = f - 1
        posCol = c + 1
        while (posFil >= 0 && posCol < filas) { // segundo hacia arriba
            if (tablero[posFil][posCol].toString().equals(ficha)) {
                cont++
            } else {

                break
            }

            posFil--
            posCol++
        }
        if (cont >= 4)
            return true
        return false
    }

    fun comprobarDiagonalDerecha(f:Int, c:Int, ficha:String) : Boolean{

        var cont = 0
        var posFil = f
        var posCol = c
        while (posFil < filas && posCol < columnas) { // primero hacia abajo
            if (tablero[posFil][posCol].toString().equals(ficha)) {
                cont++
            } else {

                break
            }

            posFil++
            posCol++

        }

        posFil = f - 1
        posCol = c - 1
        while (posFil >= 0 && posCol >= 0) { // segundo hacia arriba
            if (tablero[posFil][posCol].toString().equals(ficha)) {
                cont++
            } else {

                break
            }

            posFil--
            posCol--

        }
        if (cont >= 4)
            return true
        return false
    }

    fun comprobarHorizontal(f:Int, c:Int, ficha:String) : Boolean{

        var cont = 0
        var posFil = f
        var posCol = c
        while (posCol < columnas) { // primero hacia derecha
            if (tablero[posFil][posCol].toString().equals(ficha)) {
                cont++
            } else {

                break
            }
            posCol++
        }

        posFil = f
        posCol = c - 1
        while (posCol >= 0) { // segundo hacia izquierda
            if (tablero[posFil][posCol].toString().equals(ficha)) {
                cont++
            } else {

                break
            }
            posCol--
        }
        if (cont >= 4)
            return true
        return false
    }

    fun comprobarVertical(f:Int, c:Int, ficha:String) : Boolean{

        var cont = 0
        var posFil = f
        var posCol = c
        while (posFil < filas) { // solo hacia abajo
            if (tablero[posFil][posCol].toString().equals(ficha)) {
                cont++
            } else {

                break
            }
            posFil++

        }
        if (cont >= 4)
            return true
        return false
    }

    override fun esValido(m: Movimiento?): Boolean {
        val posicion = m.toString().toInt()

        if (posicion < 0 || posicion >= columnas) {
            return false
        }
        if (tablero[0][posicion] == 0)
            return true
        return false
    }

    fun terminarTablas(): Boolean {
        if (movimientosValidos().size == 0) {
            return true
        }
        return false
    }


    override fun tableroToString(): String {
        var str = ""
        for (i in 0 until filas) {
            for (j in 0 until columnas) {
                str += tablero[i][j]
            }
            str += "|" //caracter fin de linea
        }

        //str += estado
        //str += "|"
        //str += turno
        //str += "|"

        return str
    }

    override fun stringToTablero(cadena: String?) {
        filas = cadena?.split('|')?.size!! - 1
        columnas = cadena.split('|')[0].toCharArray().size
        tablero = ArrayList<ArrayList<Int>>()

        for (i in 0 until filas) {
            val cad = cadena.split('|')[i]
            tablero.add(ArrayList())
            for (j in 0 until columnas) {
                tablero[i].add(cad.toCharArray()[j].toInt() - 48) //viene en ascii restamos 48 para obtener nuestro numero
            }
        }
    }


}