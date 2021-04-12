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

    private val mBinding: ActivityListaTransacoesBinding by lazy {
        ActivityListaTransacoesBinding.inflate(layoutInflater)
    }
    
    private val transacoes = mutableListOf<Transacao>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = mBinding.root
        val floatingActionMenu = mBinding.listaTransacoesAdicionaMenu

        setContentView(view)
        configuraResumo()
        configuraLista()
        configuraFab(floatingActionMenu)

    }

    private fun configuraFab(floatingActionMenu: FloatingActionMenu) {
        mBinding.listaTransacoesAdicionaReceita.setOnClickListener {
            chamaDialogAdicionaTransacao(floatingActionMenu, Tipo.RECEITA)
        }

        mBinding.listaTransacoesAdicionaDespesa.setOnClickListener {
            chamaDialogAdicionaTransacao(floatingActionMenu, Tipo.DESPESA)
        }
    }

    private fun chamaDialogAdicionaTransacao(floatingActionMenu: FloatingActionMenu, tipo: Tipo) {
        AdicionaTransacaoDialog(this).chama(tipo,
            object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    adiciona(transacao)
                    floatingActionMenu.close(true)
                }
            })
    }

    private fun adiciona(transacao: Transacao) {
        transacoes.add(transacao)
        atualizaTransacoes()
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

        val listaTransacoesAdapter = ListaTransacoesAdapter(context = this, transacoes = transacoes)
        with(mBinding.listaTransacoesListview) {
            adapter = listaTransacoesAdapter
            setOnItemClickListener { _, _, position, _ ->
                val transacaoClicada = transacoes[position]
                chamaDialogAlteraTransacao(transacaoClicada, position)
            }
        }
    }

    private fun chamaDialogAlteraTransacao(
        transacaoClicada: Transacao,
        position: Int
    ) {
        AlteraTransacaoDialog(this).chama(
            transacaoClicada,
            object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    altera(transacao, position)
                }

            })
    }

    private fun altera(
        transacao: Transacao,
        position: Int
    ) {
        transacoes[position] = transacao
        atualizaTransacoes()
    }

}


