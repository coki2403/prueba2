import es.uam.eps.multij.*

class JugadorJuego(var nombreJugador:String):Jugador{

    override fun getNombre(): String {
        return nombreJugador
    }

    override fun puedeJugar(tablero: Tablero?)=true

    override fun onCambioEnPartida(evento: Evento?) {
        when(evento?.tipo){
            Evento.EVENTO_CONFIRMA->{
                /*no hace falta en esta entrega*/
            }Evento.EVENTO_TURNO->{
                var mov: Any?
                var flag: Boolean
                val ficha:String

                if(evento.partida.tablero.turno==0){
                    ficha="X"
                }else if(evento.partida.tablero.turno==1){
                    ficha="O"
                }else{
                    ficha=(evento.partida.tablero.turno+1).toString()
                }


                println("Turno de $nombreJugador. Jugando como: $ficha")
                do {
                    do{
                        flag = true
                        println("Selecciona movimiento o escriba \"guardar\" o \"salir\":")
                        mov=readLine()
                        when(mov){
                            "guardar"->{
                                evento.partida.guardarPartida()
                                println("La partida se ha guardado.")
                            }"salir"->{
                                println("Saliendo de partida")
                                return

                            }else->{
                            mov=mov?.toIntOrNull()
                            }
                        }




                    }while(mov !is Int)

                    try {
                        evento.partida.realizaAccion(AccionMover(this, MovimientoJuego(mov - 1)))
                    } catch (e: ExcepcionJuego) {
                        flag = false
                        println("El movimiento no es válido.")
                    }
                }while(flag == false)


            }Evento.EVENTO_CAMBIO->{


             }Evento.EVENTO_FIN->{


            }
        }
    }



}