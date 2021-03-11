package br.com.chicorialabs.financaskt.ui.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.chicorialabs.financaskt.R
import br.com.chicorialabs.financaskt.databinding.ActivityListaTransacoesBinding
import br.com.chicorialabs.financaskt.databinding.FormTransacaoBinding
import br.com.chicorialabs.financaskt.model.Tipo
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.ResumoView
import br.com.chicorialabs.financaskt.ui.adapter.ListaTransacoesAdapter
import br.com.chicorialabs.financaskt.ui.formataDataPadraoBrasileiro
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityListaTransacoesBinding
    val transacoes = mutableListOf<Transacao>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityListaTransacoesBinding.inflate(layoutInflater)
        val view = mBinding.root
        val floatingActionMenu = mBinding.listaTransacoesAdicionaMenu

        setContentView(view)

        configuraResumo()
        configuraLista()
        Log.i("Fin_Binding", "onCreate: $mBinding")





        mBinding.listaTransacoesAdicionaReceita.setOnClickListener {

            var dialogBinding = FormTransacaoBinding.inflate(layoutInflater)
            Log.i("Fin_Binding", "setOnClickListener: $dialogBinding")

            val hoje = Calendar.getInstance()
            val ano = 2021
            val mes = 2
            val dia = 8

            //não entendi muito bem esse pedaço:
            dialogBinding.formTransacaoData.setText(hoje.formataDataPadraoBrasileiro())
            dialogBinding.formTransacaoData.setOnClickListener {
                DatePickerDialog(this, { _, ano, mes, dia ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    dialogBinding.formTransacaoData
                        .setText(dataSelecionada.formataDataPadraoBrasileiro())
                }, ano, mes, dia)
                    .show()
            }

            val spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.categorias_de_receita, android.R.layout.simple_spinner_dropdown_item
            )
            dialogBinding.formTransacaoCategoria.adapter = spinnerAdapter

            AlertDialog.Builder(this)
                .setTitle(R.string.adiciona_receita)
                .setView(dialogBinding.root)
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Adicionar", DialogInterface.OnClickListener { dialog, which ->
                    val valorEmTexto = dialogBinding.formTransacaoValor.text.toString()
                    val categoria = dialogBinding.formTransacaoCategoria.selectedItem.toString()
                    val dataEmTexto = dialogBinding.formTransacaoData.text.toString()

                    val valor = try{
                        BigDecimal(valorEmTexto)
                    } catch (exception: NumberFormatException){
                        Toast.makeText(this, "Fomato de número inválido"
                            , Toast.LENGTH_LONG).show()
                        BigDecimal.ZERO
                    }


                    val dataConvertida: Date = SimpleDateFormat("dd/MM/yyyy").parse(dataEmTexto)
                    val data = Calendar.getInstance()
                    data.time = dataConvertida

                    val transacaoCriada = Transacao(
                        tipo = Tipo.RECEITA,
                        valor = valor,
                        categoria = categoria,
                        data = data
                    )

                    grava(transacaoCriada)
                    floatingActionMenu.close(true)

                })
                .show()
        }

        mBinding.listaTransacoesAdicionaDespesa.setOnClickListener {
            Toast.makeText(
                this, "Clicou em add despesa",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun grava(transacao: Transacao) {
        transacoes.add(transacao)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {

//        val view = window.decorView
        val resumoView = ResumoView(mBinding, transacoes, this)

        resumoView.atualiza()
    }


    private fun configuraLista() {
        mBinding.listaTransacoesListview.adapter =
            ListaTransacoesAdapter(context = this, transacoes = transacoes)
    }

    private fun transacoesDeExemplo() = listOf(
        Transacao(
            valor = BigDecimal(1000.0),
            categoria = "Aluguel",
            tipo = Tipo.DESPESA
        ),
        Transacao(
            valor = BigDecimal(50.0),
            categoria = "Bonus",
            tipo = Tipo.RECEITA
        ),
        Transacao(
            valor = BigDecimal(75.0),
            categoria = "Visita ao parque das aves",
            tipo = Tipo.DESPESA
        ),
        Transacao(
            valor = BigDecimal(10.0),
            tipo = Tipo.RECEITA
        ),
        Transacao(
            valor = BigDecimal(100.0),
            tipo = Tipo.RECEITA
        ),
    )
}