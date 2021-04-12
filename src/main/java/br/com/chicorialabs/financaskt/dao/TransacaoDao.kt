package br.com.chicorialabs.financaskt.dao

import br.com.chicorialabs.financaskt.model.Transacao

class TransacaoDao {

    val transacoes: List<Transacao> = Companion.transacoes

    companion object {
        private val transacoes: MutableList<Transacao> = mutableListOf()
    }

    fun adiciona(transacao: Transacao) {
        Companion.transacoes.add(transacao)
    }

    fun remove(position: Int){
        Companion.transacoes.removeAt(position)
    }

    fun altera(transacao: Transacao, position: Int){
        Companion.transacoes[position] = transacao
    }
}