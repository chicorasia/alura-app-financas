package br.com.chicorialabs.financaskt.ui.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
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

    private lateinit var binding: ActivityListaTransacoesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaTransacoesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        configuraResumo(transacoes)
        configuraLista(transacoes)

        binding.listaTransacoesAdicionaReceita.setOnClickListener {

            var binding = FormTransacaoBinding.inflate(layoutInflater)

            val hoje = Calendar.getInstance()
            val ano = 2021
            val mes = 2
            val dia = 8

            //não entendi muito bem esse pedaço:
            binding.formTransacaoData.setText(hoje.formataDataPadraoBrasileiro())
            binding.formTransacaoData.setOnClickListener {
                DatePickerDialog(this, { _, ano, mes, dia ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    binding.formTransacaoData
                        .setText(dataSelecionada.formataDataPadraoBrasileiro())
                }, ano, mes, dia)
                    .show()
            }

            val spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.categorias_de_receita, android.R.layout.simple_spinner_dropdown_item
            )
            binding.formTransacaoCategoria.adapter = spinnerAdapter

            AlertDialog.Builder(this)
                .setTitle(R.string.adiciona_receita)
                .setView(binding.root)
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Adicionar", DialogInterface.OnClickListener { dialog, which ->
                    val valorEmTexto = binding.formTransacaoValor.text.toString()
                    val categoria = binding.formTransacaoCategoria.selectedItem.toString()
                    val dataEmTexto = binding.formTransacaoData.text.toString()

                    val valor = BigDecimal(valorEmTexto)
                    val dataConvertida: Date = SimpleDateFormat("dd/MM/yyyy").parse(dataEmTexto)
                    val data = Calendar.getInstance()
                    data.time = dataConvertida

                    val transacaoCriada = Transacao(
                        tipo = Tipo.RECEITA,
                        valor = valor,
                        categoria = categoria,
                        data = data
                    )

                    Toast.makeText(this, "${transacaoCriada.valor} - " +
                            "${transacaoCriada. data.formataDataPadraoBrasileiro()} - " +
                            "${transacaoCriada.tipo} - " +
                            "${transacaoCriada.categoria}", Toast.LENGTH_SHORT).show()
                })
                .show()
        }

        binding.listaTransacoesAdicionaDespesa.setOnClickListener {
            Toast.makeText(
                this, "Clicou em add despesa",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun configuraResumo(transacoes: List<Transacao>) {

//        val view = window.decorView
        val resumoView = ResumoView(binding, transacoes, this)

        resumoView.atualiza()
    }


    private fun configuraLista(transacoes: List<Transacao>) {
        binding.listaTransacoesListview.adapter =
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