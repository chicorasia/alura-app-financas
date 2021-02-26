package br.com.chicorialabs.financaskt.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.chicorialabs.financaskt.R
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.formataDataPadraoBrasileiro
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(transacoes: List<Transacao>,
                             context: Context) : BaseAdapter() {

    private val transacoes = transacoes
    private val context = context

    override fun getCount(): Int = transacoes.size

    override fun getItem(posicao: Int): Transacao = transacoes[posicao]

    override fun getItemId(posicao: Int): Long {
        return 0
    }

    override fun getView(posicao: Int, convertView: View?, parent: ViewGroup?): View {
        val viewCriada =  LayoutInflater.from(context).inflate(R.layout.transacao_item,
            parent, false)

        val transacao = transacoes[posicao]

        viewCriada.transacao_valor.text = transacao.valor.toString()
        viewCriada.transacao_categoria.text = transacao.categoria
        viewCriada.transacao_data.text = transacao.data.formataDataPadraoBrasileiro()

        return viewCriada
    }


}