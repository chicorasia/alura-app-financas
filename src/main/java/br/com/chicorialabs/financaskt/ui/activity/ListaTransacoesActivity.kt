package br.com.chicorialabs.financaskt.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import br.com.chicorialabs.financaskt.dao.TransacaoDao
import br.com.chicorialabs.financaskt.databinding.ActivityListaTransacoesBinding
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

    private val dao = TransacaoDao()
    private val transacoes = dao.transacoes

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
        AdicionaTransacaoDialog(this).chama(tipo)
        { transacaoCriada ->
            adiciona(transacaoCriada)
            floatingActionMenu.close(true)
        }
    }


    private fun adiciona(transacao: Transacao) {
        dao.adiciona(transacao)
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
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        val idDoMenu = item.itemId
        if (idDoMenu == 1) {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterMenuInfo.position
            remove(posicaoDaTransacao)
        }
        return super.onContextItemSelected(item)
    }

    private fun remove(posicao: Int) {
        dao.remove(posicao)
        atualizaTransacoes()
    }

    private fun chamaDialogAlteraTransacao(
        transacaoClicada: Transacao,
        position: Int
    ) {
        AlteraTransacaoDialog(this).chama(transacaoClicada) { transacaoAlterada ->
            altera(transacaoAlterada, position)
        }
    }

    private fun altera(
        transacao: Transacao,
        position: Int
    ) {
        dao.altera(transacao, position)
        atualizaTransacoes()
    }

}


