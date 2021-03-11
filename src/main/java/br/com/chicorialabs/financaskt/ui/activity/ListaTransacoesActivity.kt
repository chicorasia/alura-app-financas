package br.com.chicorialabs.financaskt.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.chicorialabs.financaskt.databinding.ActivityListaTransacoesBinding
import br.com.chicorialabs.financaskt.delegate.TransacaoDelegate
import br.com.chicorialabs.financaskt.model.Tipo
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.ResumoView
import br.com.chicorialabs.financaskt.ui.adapter.ListaTransacoesAdapter
import br.com.chicorialabs.financaskt.ui.dialog.AdicionaTransacaoDialog
import java.math.BigDecimal

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

            AdicionaTransacaoDialog(this).configuraDialog(
                object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        grava(transacao)
                        floatingActionMenu.close(true)
                    }

                })
        }

        mBinding.listaTransacoesAdicionaDespesa.setOnClickListener {
            Toast.makeText(
                this, "Clicou em add despesa",
                Toast.LENGTH_LONG
            ).show()
        }

    }


    fun grava(transacao: Transacao) {
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


