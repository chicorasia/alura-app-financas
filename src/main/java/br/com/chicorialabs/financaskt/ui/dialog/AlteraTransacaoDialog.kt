package br.com.chicorialabs.financaskt.ui.dialog

import android.content.Context
import br.com.chicorialabs.financaskt.delegate.TransacaoDelegate
import br.com.chicorialabs.financaskt.model.Tipo
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.formataDataPadraoBrasileiro

class AlteraTransacaoDialog(val context: Context) : FormularioTransacaoDialog(context = context) {

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    override fun tituloPor(tipo: Tipo): String {
        if (tipo == Tipo.RECEITA) {
            return "Alterar Receita"
        } else
            return "Alterar Despesa"
    }


    fun chama(
        transacao: Transacao,
        transacaoDelegate: TransacaoDelegate
    ) {
        val tipo = transacao.tipo

        super.chama(tipo, transacaoDelegate)

        inicializaCampos(transacao, tipo)
    }

    internal fun inicializaCampos(
        transacao: Transacao,
        tipo: Tipo
    ) {
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoTipo(tipo, transacao)
    }

    internal fun inicializaCampoTipo(
        tipo: Tipo,
        transacao: Transacao
    ) {
        val categoriasRecebidas = context.resources.getStringArray(tipo.categorias)
        val posicaoCategoria = categoriasRecebidas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataDataPadraoBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }


}