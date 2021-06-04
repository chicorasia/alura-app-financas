package br.com.chicorialabs.financaskt.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import br.com.chicorialabs.financaskt.dao.TransacaoDao
import br.com.chicorialabs.financaskt.dao.TransacaoDatabase
import br.com.chicorialabs.financaskt.databinding.ActivityListaTransacoesBinding
import br.com.chicorialabs.financaskt.model.Tipo
import br.com.chicorialabs.financaskt.model.Transacao
import br.com.chicorialabs.financaskt.ui.ResumoView
import br.com.chicorialabs.financaskt.ui.adapter.ListaTransacoesAdapter
import br.com.chicorialabs.financaskt.ui.dialog.AdicionaTransacaoDialog
import br.com.chicorialabs.financaskt.ui.dialog.AlteraTransacaoDialog
import com.github.clans.fab.FloatingActionMenu
import kotlinx.coroutines.launch

class ListaTransacoesActivity : AppCompatActivity() {

    private val mBinding: ActivityListaTransacoesBinding by lazy {
        ActivityListaTransacoesBinding.inflate(layoutInflater)
    }

    private lateinit var dao: TransacaoDao
    private lateinit var transacoes: LiveData<List<Transacao>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = mBinding.root
        val floatingActionMenu = mBinding.listaTransacoesAdicionaMenu

        val application = requireNotNull(this).application
        dao = TransacaoDatabase.getInstance(application).transacaoDao

        lifecycleScope.launch {
            transacoes = dao.todas()
            configuraResumo()
            configuraLista()
        }


        setContentView(view)
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
        lifecycleScope.launch {
            dao.adiciona(transacao)
        }
        atualizaTransacoes()
    }


    fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        transacoes.observe(this) {
            val resumoView = ResumoView(mBinding, it, this@ListaTransacoesActivity)
            resumoView.atualiza()
        }



    }


    private fun configuraLista() {

        transacoes.observe(this) {
            val listaTransacoesAdapter = ListaTransacoesAdapter(context = this, transacoes = it)
            with(mBinding.listaTransacoesListview) {
                adapter = listaTransacoesAdapter
                setOnItemClickListener { _, _, position, _ ->
                    val transacaoClicada = it[position]
                    chamaDialogAlteraTransacao(transacaoClicada)
                }
                setOnCreateContextMenuListener { menu, _, _ ->
                    menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
                }
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
        transacoes?.let { it ->
            val id = it.value?.get(posicao)?.transacaoId
            lifecycleScope.launch {
                id?.let { id ->
                    dao.remove(id)
                }
            }
            atualizaTransacoes()
        }

    }

    private fun chamaDialogAlteraTransacao(
        transacaoClicada: Transacao,
    ) {
        AlteraTransacaoDialog(this).chama(transacaoClicada) { transacaoAlterada ->
            altera(transacaoAlterada)
        }
    }

    private fun altera(
        transacao: Transacao,
    ) {
        lifecycleScope.launch {
            dao.altera(transacao)
        }
        atualizaTransacoes()
    }


}


