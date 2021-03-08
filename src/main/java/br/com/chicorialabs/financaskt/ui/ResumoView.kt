package br.com.chicorialabs.financaskt.ui

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import br.com.chicorialabs.financaskt.R
import br.com.chicorialabs.financaskt.extension.formataMoedaPadraoBrasileiro
import br.com.chicorialabs.financaskt.model.Resumo
import br.com.chicorialabs.financaskt.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(
    private val view: View,
    transacoes: List<Transacao>,
    context: Context
) {
    private val resumo: Resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)


    fun atualiza(){
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    private fun adicionaReceita() {
        val totalReceita = resumo.receita
        with(view.resumo_card_receita) {
            setTextColor(corReceita)
            text = totalReceita.formataMoedaPadraoBrasileiro()
        }
    }

    private fun adicionaDespesa() {

        val totalDespesa = resumo.despesa
        with(view.resumo_card_despesa){
            setTextColor(corDespesa)
            text = totalDespesa.formataMoedaPadraoBrasileiro()
        }
    }

    private fun adicionaTotal() {

        val total = resumo.total()
        with(view.resumo_card_total) {
            setTextColor(corPor(total))
            resumo_card_total.text = total.formataMoedaPadraoBrasileiro()
        }
    }

    private fun corPor(valor: BigDecimal) : Int {
        if (valor >= BigDecimal.ZERO) {
            return corReceita
        }
        return corDespesa
    }
}