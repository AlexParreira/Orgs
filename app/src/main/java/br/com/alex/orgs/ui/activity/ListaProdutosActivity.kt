package br.com.alex.orgs.ui.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import br.com.alex.orgs.R
import br.com.alex.orgs.database.AppDatabase
import br.com.alex.orgs.databinding.ActivityListaProdutosBinding
import br.com.alex.orgs.extensions.vaiPara
import br.com.alex.orgs.preferences.dataStore
import br.com.alex.orgs.preferences.usuarioLogadoPreferences
import br.com.alex.orgs.ui.recycleView.adapter.ListaProdutoAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

private const val TAG = "ListaProdutosActivity"

class ListaProdutosActivity : UsuarioBaseActivity() {

    private val adapter = ListaProdutoAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosBinding
            .inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDatabase.instacia(this)
            .produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecicleView()
        configuraFab()
        lifecycle.coroutineScope.launch {

            launch {
                usuario
                    .filterNotNull()
                    .collect {usuario->
                    buscaProdutoUsuario(usuario.id)
                }
            }
        }
    }





    private suspend fun buscaProdutoUsuario(usuarioID: String) {
        produtoDao.buscaTodosDoUsuario(usuarioID).collect { produtos ->
            adapter.atualiza(produtos)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_lista_produtos_ordenar -> {
                lifecycle.coroutineScope.launch {
                    deslogaUsuario()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


/*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val produtosOrdenado: List<Produto>? = when (item.itemId) {
            R.id.menu_lista_produtos_ordenar_nome_asc ->
                produtoDao.buscaTodosOrdenadorPorNomeAsc()
            R.id.menu_lista_produtos_ordenar_nome_desc ->
                produtoDao.buscaTodosOrdenadorPorNomeDesc()
            R.id.menu_lista_produtos_ordenar_descricao_asc ->
                produtoDao.buscaTodosOrdenadorPorDescricaoAsc()
            R.id.menu_lista_produtos_ordenar_descricao_desc ->
                produtoDao.buscaTodosOrdenadorPorDescricaoDesc()
            R.id.menu_lista_produtos_ordenar_valor_asc ->
                produtoDao.buscaTodosOrdenadorPorValorAsc()
            R.id.menu_lista_produtos_ordenar_valor_desc ->
                produtoDao.buscaTodosOrdenadorPorValorDesc()
            R.id.menu_lista_produtos_ordenar_sem_ordem ->
                null
            else -> null
        }
        produtosOrdenado?.let {
            adapter.atualiza(it)
        }
        return super.onOptionsItemSelected(item)
    }
*/


    private fun configuraFab() {
        val fab = binding.activityListaProdutosFloatingActionButton
        fab.setOnClickListener {
            vaiParaFormularioProduto()

        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecicleView() {
        val recyclerView = binding.activityListaProdutosRecycleView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalheProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
        adapter.quandoClicarEmEditar = {
            Log.i(TAG, "configuraRecyclerView: Editar $it")
        }
        adapter.quandoClicarEmRemover = {
            Log.i(TAG, "configuraRecyclerView: Remover $it")
        }
    }
}
