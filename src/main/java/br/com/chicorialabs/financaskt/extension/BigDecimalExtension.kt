package br.com.chicorialabs.financaskt.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun BigDecimal.formataMoedaPadraoBrasileiro(): String {

    return DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
        .format(this)
        .replace("R$", "R$ ")

}