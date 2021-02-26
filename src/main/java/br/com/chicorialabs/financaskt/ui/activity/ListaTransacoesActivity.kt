package br.com.chicorialabs.financaskt.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.chicorialabs.financaskt.R
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)



        val transacoes: List<Transacao> = listOf(
            Transacao(valor = BigDecimal(1000.0), categoria = "Aluguel", data = Calendar.getInstance()),
            Transacao(valor = BigDecimal(50.0), categoria = "Bonus", data = Calendar.getInstance()))


        lista_transacoes_listview.adapter =
            ListaTransacoesAdapter( context = this, transacoes = transacoes)

    }
}