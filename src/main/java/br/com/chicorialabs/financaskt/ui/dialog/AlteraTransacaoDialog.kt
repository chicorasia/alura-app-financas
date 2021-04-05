package br.com.chicorialabs.financaskt.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.chicorialabs.financaskt.databinding.FormTransacaoBinding
import br.com.chicorialabs.financaskt.delegate.TransacaoDelegate
import br.com.chicorialabs.financaskt.extension.converteParaCalendar
import br.com.chicorialabs.financaskt.model.Tipo
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.formataDataPadraoBrasileiro
import java.math.BigDecimal
import java.util.*

class AlteraTransacaoDialog(private val context: Context) {

    var dialogBinding = criaViewDialog(context)

    private val campoCategoria = dialogBinding.formTransacaoCategoria
    private val campoValor = dialogBinding.formTransacaoValor
    private val campoData = dialogBinding.formTransacaoData

    fun chama(
        transacao: Transacao,
        transacaoDelegate: TransacaoDelegate
    ) {
        val tipo = transacao.tipo

        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormTransacao(tipo, transacaoDelegate)

        campoValor.setText(transacao.valor.toString())
        campoData.setText(transacao.data.formataDataPadraoBrasileiro())
        val categoriasRecebidas = context.resources.getStringArray(tipo.categorias)
        val posicaoCategoria = categoriasRecebidas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }


    private fun configuraFormTransacao(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        AlertDialog.Builder(context)
            .setTitle(tipo.titulo)
            .setView(dialogBinding.root)
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Alterar", DialogInterface.OnClickListener { _, _ ->
                val valorEmTexto = campoValor.text.toString()
                val categoria = campoCategoria.selectedItem.toString()
                val dataEmTexto = campoData.text.toString()

                val valor = converteValorEmBigDecimal(valorEmTexto)

                val data = Calendar.getInstance()
                data.time = dataEmTexto.converteParaCalendar()

                val transacaoCriada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    categoria = categoria,
                    data = data
                )

                transacaoDelegate.delegate(transacaoCriada)

            })
            .show()
    }


    private fun converteValorEmBigDecimal(valorEmTexto: String): BigDecimal = try {
        BigDecimal(valorEmTexto)
    } catch (exception: NumberFormatException) {
        Toast.makeText(
            context, "Fomato de número inválido", Toast.LENGTH_LONG
        ).show()
        BigDecimal.ZERO
    }

    private fun configuraCampoCategoria(tipo: Tipo) {

        val spinnerAdapter = ArrayAdapter.createFromResource(
            context,
            tipo.categorias, android.R.layout.simple_spinner_dropdown_item
        )
        campoCategoria.adapter = spinnerAdapter
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formataDataPadraoBrasileiro())
        campoData.setOnClickListener {
            DatePickerDialog(context, { _, ano, mes, dia ->
                val dataSelecionada = Calendar.getInstance()
                dataSelecionada.set(ano, mes, dia)
                campoData.setText(dataSelecionada.formataDataPadraoBrasileiro())
            }, ano, mes, dia)
                .show()
        }
    }

    private fun criaViewDialog(context: Context): FormTransacaoBinding {
        var dialogBinding = FormTransacaoBinding.inflate(LayoutInflater.from(context))
        return dialogBinding
    }
}