package com.joaquinalejandro.practica2

import java.util.*

class PartidaLista(val titleC : String,val dateC : String,val playersC : String,val idC:String) {

    var title : String
    var date : String
    var players : String
    var id:String

    init{
        title = titleC //hay que cogerlo de algun sitio
        date=dateC
        players=playersC
        id=idC
    }
}