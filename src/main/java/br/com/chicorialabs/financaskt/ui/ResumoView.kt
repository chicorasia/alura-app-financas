package br.com.chicorialabs.financaskt.ui

import android.view.View
import br.com.chicorialabs.financaskt.extension.formataMoedaPadraoBrasileiro
import br.com.chicorialabs.financaskt.model.Resumo
import br.com.chicorialabs.financaskt.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*

class ResumoView(
    private val view: View,
    transacoes: List<Transacao>) {

    private val resumo: Resumo = Resumo(transacoes)

    fun adicionaReceita() {
        val totalReceita = resumo.receita()
        view.resumo_card_receita.text = totalReceita.formataMoedaPadraoBrasileiro()
    }

    fun adicionaDespesa() {

        val totalDespesa = resumo.despesa()
        view.resumo_card_despesa.text = totalDespesa.formataMoedaPadraoBrasileiro()
    }

    fun adicionaTotal() {

        val total = resumo.total()
        view.resumo_card_total.text = total.formataMoedaPadraoBrasileiro()
    }
}