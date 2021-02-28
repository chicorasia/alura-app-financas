package br.com.chicorialabs.financaskt.extension

fun String.limitaEmAte(caracteres: Int): String{

    if (this.length > caracteres) {
        val caracterInicial = 0
        return "${this.substring(caracterInicial..caracteres)}..."
    }
    return this

}