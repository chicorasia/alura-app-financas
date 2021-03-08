package br.com.chicorialabs.financaskt.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.chicorialabs.financaskt.databinding.TransacaoItemBinding
import br.com.chicorialabs.financaskt.extension.formataMoedaPadraoBrasileiro
import br.com.chicorialabs.financaskt.extension.limitaEmAte
import br.com.chicorialabs.financaskt.model.Tipo
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.formataDataPadraoBrasileiro


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

        var binding = TransacaoItemBinding.inflate(LayoutInflater.from(context))
        val viewCriada = binding.root

//        val viewCriada =  LayoutInflater.from(context).inflate(R.layout.transacao_item,
//            parent, false).rootView

        val transacao = transacoes[posicao]

        adicionaIcone(transacao, binding)
        adicionaValor(binding, transacao)
        adicionaCategoria(binding, transacao)
        adicionaData(binding, transacao)

        return viewCriada
    }

    private fun adicionaIcone(
        transacao: Transacao,
        binding: TransacaoItemBinding
    ) {
        transacao.tipo.let { tipo: Tipo ->
            binding.transacaoIcone.setBackgroundResource(tipo.icone)
        }
    }

    private fun adicionaValor(
        binding: TransacaoItemBinding,
        transacao: Transacao
    ) {
        binding.transacaoValor.text =
            transacao.valor.formataMoedaPadraoBrasileiro()

        transacao.tipo.let { tipo: Tipo ->
            binding.transacaoValor.setTextColor(context.getColor(tipo.cor))
        }
    }

    private fun adicionaData(
        binding: TransacaoItemBinding,
        transacao: Transacao
    ) {
        binding.transacaoData.text = transacao.data.formataDataPadraoBrasileiro()
    }

    private fun adicionaCategoria(
        binding: TransacaoItemBinding,
        transacao: Transacao
    ) {
        binding.transacaoCategoria.text = transacao.categoria.limitaEmAte(charMaximoCategoria)
    }


}