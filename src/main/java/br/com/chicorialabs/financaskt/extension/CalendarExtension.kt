package br.com.chicorialabs.financaskt.ui

import java.text.SimpleDateFormat
import java.util.*

const val PADRAO_DATA_BRASILEIRO = "dd/MM/yyyy"

fun Calendar.formataDataPadraoBrasileiro() : String =
    SimpleDateFormat(PADRAO_DATA_BRASILEIRO).format(this.time)