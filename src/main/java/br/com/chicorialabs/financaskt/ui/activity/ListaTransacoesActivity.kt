package br.com.chicorialabs.financaskt.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.chicorialabs.financaskt.R
import br.com.chicorialabs.financaskt.model.Tipo
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.ResumoView
import br.com.chicorialabs.financaskt.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal

class ListaTransacoesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()
        val view = window.decorView

        configuraResumo(view, transacoes)
        configuraLista(transacoes)

    }

    private fun configuraResumo(
        view: View,
        transacoes: List<Transacao>
    ) {
        ResumoView(view, transacoes).adicionaReceita()
        ResumoView(view, transacoes).adicionaDespesa()
        ResumoView(view, transacoes).adicionaTotal()
    }


    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter =
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
    )
}