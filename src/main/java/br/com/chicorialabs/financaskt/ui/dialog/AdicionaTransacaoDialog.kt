package br.com.chicorialabs.financaskt.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.chicorialabs.financaskt.R
import br.com.chicorialabs.financaskt.databinding.FormTransacaoBinding
import br.com.chicorialabs.financaskt.delegate.TransacaoDelegate
import br.com.chicorialabs.financaskt.extension.converteParaCalendar
import br.com.chicorialabs.financaskt.model.Tipo
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.formataDataPadraoBrasileiro
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(private val context: Context) {

    var dialogBinding = criaViewDialog(context)

    fun configuraDialog(transacaoDelegate: TransacaoDelegate) {


        configuraCampoData()
        configuraCampoCategoria()
        configuraFormTransacao(transacaoDelegate)
    }

    private fun configuraFormTransacao(transacaoDelegate: TransacaoDelegate) {
        AlertDialog.Builder(context)
            .setTitle(R.string.adiciona_receita)
            .setView(dialogBinding.root)
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Adicionar", DialogInterface.OnClickListener { dialog, which ->
                val valorEmTexto = dialogBinding.formTransacaoValor.text.toString()
                val categoria = dialogBinding.formTransacaoCategoria.selectedItem.toString()
                val dataEmTexto = dialogBinding.formTransacaoData.text.toString()

                val valor = converteValorEmBigDecimal(valorEmTexto)

                val data = Calendar.getInstance()
                data.time = dataEmTexto.converteParaCalendar()

                val transacaoCriada = Transacao(
                    tipo = Tipo.RECEITA,
                    valor = valor,
                    categoria = categoria,
                    data = data
                )

                transacaoDelegate.delegate(transacaoCriada)

            })
            .show()
    }




    private fun converteValorEmBigDecimal(valorEmTexto: String): BigDecimal =  try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(
                context, "Fomato de número inválido", Toast.LENGTH_LONG
            ).show()
            BigDecimal.ZERO
        }

    private fun configuraCampoCategoria() {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.categorias_de_receita, android.R.layout.simple_spinner_dropdown_item
        )
        dialogBinding.formTransacaoCategoria.adapter = spinnerAdapter
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        dialogBinding.formTransacaoData.setText(hoje.formataDataPadraoBrasileiro())
        dialogBinding.formTransacaoData.setOnClickListener {
            DatePickerDialog(context, { _, ano, mes, dia ->
                val dataSelecionada = Calendar.getInstance()
                dataSelecionada.set(ano, mes, dia)
                dialogBinding.formTransacaoData
                    .setText(dataSelecionada.formataDataPadraoBrasileiro())
            }, ano, mes, dia)
                .show()
        }
    }

    private fun criaViewDialog(context: Context): FormTransacaoBinding {
        var dialogBinding = FormTransacaoBinding.inflate(LayoutInflater.from(context))
        return dialogBinding
    }
}