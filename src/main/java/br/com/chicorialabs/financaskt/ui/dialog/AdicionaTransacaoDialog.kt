package br.com.chicorialabs.financaskt.ui.dialog

import android.content.Context
import br.com.chicorialabs.financaskt.model.Tipo

class AdicionaTransacaoDialog(context: Context) : FormularioTransacaoDialog(context) {
    override val tituloBotaoPositivo: String
        get() = "Adicionar"


    override fun tituloPor(tipo: Tipo): String {
        if (tipo == Tipo.RECEITA) {
            return "Adicionar Receita"
        } else
            return "Adicionar Despesa"
    }
}