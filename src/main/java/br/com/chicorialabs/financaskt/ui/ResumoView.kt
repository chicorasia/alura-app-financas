package br.com.chicorialabs.financaskt.ui

//import kotlinx.android.synthetic.main.resumo_card.view.*
import android.content.Context
import androidx.core.content.ContextCompat
import br.com.chicorialabs.financaskt.R
import br.com.chicorialabs.financaskt.databinding.ActivityListaTransacoesBinding
import br.com.chicorialabs.financaskt.extension.formataMoedaPadraoBrasileiro
import br.com.chicorialabs.financaskt.model.Resumo
import br.com.chicorialabs.financaskt.model.Transacao
import java.math.BigDecimal

class ResumoView(
    private val binding: ActivityListaTransacoesBinding,
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
        with(binding.listaTransacoesResumo.resumoCardReceita) {
            setTextColor(corReceita)
            text = totalReceita.formataMoedaPadraoBrasileiro()
        }
    }

    private fun adicionaDespesa() {

        val totalDespesa = resumo.despesa
        with(binding.listaTransacoesResumo.resumoCardDespesa){
            setTextColor(corDespesa)
            text = totalDespesa.formataMoedaPadraoBrasileiro()
        }
    }

    private fun adicionaTotal() {

        val total = resumo.total()
        with(binding.listaTransacoesResumo.resumoCardTotal) {
            setTextColor(corPor(total))
            text = total.formataMoedaPadraoBrasileiro()
        }
    }

    private fun corPor(valor: BigDecimal) : Int {
        if (valor >= BigDecimal.ZERO) {
            return corReceita
        }
        return corDespesa
    }
}