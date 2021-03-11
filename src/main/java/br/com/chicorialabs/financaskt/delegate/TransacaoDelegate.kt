package br.com.chicorialabs.financaskt.delegate

import br.com.chicorialabs.financaskt.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacao: Transacao)
}