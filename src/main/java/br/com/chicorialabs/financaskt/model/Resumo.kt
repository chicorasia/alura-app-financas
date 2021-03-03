package br.com.chicorialabs.financaskt.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    val despesa get() = somaPor(Tipo.DESPESA)

    val receita get() = somaPor(Tipo.RECEITA)

    private fun somaPor(tipo: Tipo) : BigDecimal =
        transacoes.filter { it.tipo == tipo }
            .sumOf { it.valor }

    fun total() : BigDecimal = receita.subtract(despesa)
}