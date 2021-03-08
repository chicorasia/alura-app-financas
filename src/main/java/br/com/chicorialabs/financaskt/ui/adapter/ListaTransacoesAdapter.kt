package br.com.chicorialabs.financaskt.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.chicorialabs.financaskt.R
import br.com.chicorialabs.financaskt.extension.formataMoedaPadraoBrasileiro
import br.com.chicorialabs.financaskt.extension.limitaEmAte
import br.com.chicorialabs.financaskt.model.Tipo
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.formataDataPadraoBrasileiro
import kotlinx.android.synthetic.main.transacao_item.view.*


class ListaTransacoesAdapter(val transacoes: List<Transacao>,
                             val context: Context) : BaseAdapter() {

    private val charMaximoCategoria = 14

    override fun getCount(): Int = transacoes.size

    override fun getItem(posicao: Int): Transacao = transacoes[posicao]

    override fun getItemId(posicao: Int): Long {
        return 0
    }


    override fun getView(
        posicao: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val viewCriada =  LayoutInflater.from(context).inflate(R.layout.transacao_item,
            parent, false).rootView

        val transacao = transacoes[posicao]

        adicionaIcone(transacao, viewCriada)
        adicionaValor(viewCriada, transacao)
        adicionaCategoria(viewCriada, transacao)
        adicionaData(viewCriada, transacao)

        return viewCriada
    }

    private fun adicionaIcone(
        transacao: Transacao,
        viewCriada: View
    ) {
        transacao.tipo.let { tipo: Tipo ->

            viewCriada.transacao_icone.setBackgroundResource(tipo.icone)
        }
    }

    private fun adicionaValor(
        viewCriada: View,
        transacao: Transacao
    ) {
        viewCriada.transacao_valor.text =
            transacao.valor.formataMoedaPadraoBrasileiro()

        transacao.tipo.let { tipo: Tipo ->
            viewCriada.transacao_valor.setTextColor(context.getColor(tipo.cor))
        }
    }

    private fun adicionaData(
        viewCriada: View,
        transacao: Transacao
    ) {
        viewCriada.transacao_data.text = transacao.data.formataDataPadraoBrasileiro()
    }

    private fun adicionaCategoria(
        viewCriada: View,
        transacao: Transacao
    ) {
        viewCriada.transacao_categoria.text = transacao.categoria.limitaEmAte(charMaximoCategoria)
    }


}