package br.com.chicorialabs.financaskt.model

import br.com.chicorialabs.financaskt.R

enum class Tipo(val cor: Int, val icone: Int) {
    RECEITA(R.color.receita, R.drawable.icone_transacao_item_receita),
    DESPESA (R.color.despesa, R.drawable.icone_transacao_item_despesa)
}
