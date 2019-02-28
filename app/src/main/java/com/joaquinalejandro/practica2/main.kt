import es.uam.eps.multij.Jugador
import es.uam.eps.multij.JugadorAleatorio
import es.uam.eps.multij.Partida

fun main(args: Array<String>) {

    Menu()

}



fun Menu(){
    var mov:Any?
    do {
        println("\n\n\n\t\tCONECTA 4")
        println("Menu:")
        println("1- Comenzar nuevo juego")
        println("2- Continuar partida")
        println("3- Salir del juego")
        mov=readLine()?.toIntOrNull()?:println("comando invalido. Introduzca un entero.")
        when(mov){
            1->MenuComenzar()
            2->Reanudar()
            3-> System.exit(0)

        }

    } while (mov !is Int ||mov!=3)

}
fun Reanudar(){

    var partida= Partida(null,null)
    partida=partida.cargarPartida()
    partida.comenzar()
}


fun MenuComenzar(){
    var mov:Any?
    do {
        println("\nIntroduce el numero de jugadores:")
        println("1: contra la maquina\t2:dos jugadores humanos")
        mov=readLine()?.toIntOrNull()?:println("comando invalido. Introduzca un entero.")
    } while (mov !is Int )

    when(mov){
        1->{
            println("Introduce el nombre del jugador:")
            val nombre= readLine()!!
            val listaJugadores = arrayListOf<Jugador>()
            listaJugadores.add(JugadorJuego( nombre))
            listaJugadores.add(JugadorAleatorio("maquina"))

            val partida= Partida(TableroJuego(5,7),listaJugadores)
            partida.addObservador(ObservadorJuego())

            partida.comenzar()



        }
        2->{
            println("Introduce el nombre del primer jugador:")
            val nombre1= readLine()!!

            println("Introduce el nombre del segundo jugador:")
            val nombre2= readLine()!!
            val listaJugadores = arrayListOf<Jugador>()
            listaJugadores.add(JugadorJuego( nombre1))
            listaJugadores.add(JugadorJuego( nombre2))

            val partida= Partida(TableroJuego(5,7),listaJugadores)
            partida.addObservador(ObservadorJuego())

            partida.comenzar()

        }

    }



}