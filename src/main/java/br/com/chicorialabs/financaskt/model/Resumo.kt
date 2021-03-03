package br.com.chicorialabs.financaskt.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    fun despesa() : BigDecimal {
        var totalDespesa = BigDecimal.ZERO
        for (transacao in transacoes.filter { it ->
            it.tipo == Tipo.DESPESA
        }) { totalDespesa += transacao.valor }
        return totalDespesa
    }

    fun receita() : BigDecimal {
        var totalReceita = BigDecimal.ZERO
        for (transacao in transacoes.filter { it ->
            it.tipo == Tipo.RECEITA
        }) { totalReceita += transacao.valor }
        return totalReceita
    }

    fun total() : BigDecimal = receita().subtract(despesa())
}