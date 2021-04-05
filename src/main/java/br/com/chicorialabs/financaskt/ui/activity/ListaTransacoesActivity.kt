package br.com.chicorialabs.financaskt.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.chicorialabs.financaskt.databinding.ActivityListaTransacoesBinding
import br.com.chicorialabs.financaskt.delegate.TransacaoDelegate
import br.com.chicorialabs.financaskt.model.Tipo
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.ResumoView
import br.com.chicorialabs.financaskt.ui.adapter.ListaTransacoesAdapter
import br.com.chicorialabs.financaskt.ui.dialog.AdicionaTransacaoDialog
import br.com.chicorialabs.financaskt.ui.dialog.AlteraTransacaoDialog
import com.github.clans.fab.FloatingActionMenu

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
        configuraFab(floatingActionMenu)

    }

    private fun configuraFab(floatingActionMenu: FloatingActionMenu) {
        mBinding.listaTransacoesAdicionaReceita.setOnClickListener {
            chama(floatingActionMenu, Tipo.RECEITA)
        }

        mBinding.listaTransacoesAdicionaDespesa.setOnClickListener {
            chama(floatingActionMenu, Tipo.DESPESA)
        }
    }

    private fun chama(floatingActionMenu: FloatingActionMenu, tipo: Tipo) {
        AdicionaTransacaoDialog(this).chama(tipo,
            object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    transacoes.add(transacao)
                    atualizaTransacoes()
                    floatingActionMenu.close(true)
                }

            })
    }


    fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(mBinding, transacoes, this)

        resumoView.atualiza()
    }


    private fun configuraLista() {
        mBinding.listaTransacoesListview.adapter =
            ListaTransacoesAdapter(context = this, transacoes = transacoes)

        mBinding.listaTransacoesListview.setOnItemClickListener { parent, view, position, id ->
            val transacaoClicada = transacoes[position]

            AlteraTransacaoDialog(this).chama(transacaoClicada,
                object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    transacoes[position] = transacao
                    atualizaTransacoes()
                }

            })

        }

    }

}


