package br.com.chicorialabs.financaskt.ui.activity

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.chicorialabs.financaskt.R
import br.com.chicorialabs.financaskt.databinding.ActivityListaTransacoesBinding
import br.com.chicorialabs.financaskt.databinding.FormTransacaoBinding
import br.com.chicorialabs.financaskt.model.Tipo
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.ResumoView
import br.com.chicorialabs.financaskt.ui.adapter.ListaTransacoesAdapter
import java.math.BigDecimal

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

            AlertDialog.Builder(this)
                .setTitle(R.string.adiciona_receita)
                .setView(binding.root)
                .setNegativeButton("cancelar", null)
                .setPositiveButton("pronto", null)
                .show()
        }

        binding.listaTransacoesAdicionaDespesa.setOnClickListener {
            Toast.makeText(this, "Clicou em add despesa",
                Toast.LENGTH_LONG).show()
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