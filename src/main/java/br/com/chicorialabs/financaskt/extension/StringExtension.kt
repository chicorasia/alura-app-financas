package br.com.chicorialabs.financaskt.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caracteres: Int): String{

    if (this.length > caracteres) {
        val caracterInicial = 0
        return "${this.substring(caracterInicial..caracteres)}..."
    }
    return this

}

fun String.converteParaCalendar(): Date =
    SimpleDateFormat("dd/MM/yyyy").parse(this)