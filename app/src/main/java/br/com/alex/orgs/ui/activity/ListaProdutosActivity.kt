package br.com.alex.orgs.ui.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alex.orgs.R
import br.com.alex.orgs.database.AppDatabase
import br.com.alex.orgs.database.dao.UsuarioDao
import br.com.alex.orgs.databinding.ActivityListaProdutosBinding
import br.com.alex.orgs.extensions.vaiPara
import br.com.alex.orgs.model.Produto
import br.com.alex.orgs.preferences.dataStore
import br.com.alex.orgs.preferences.usuarioLogadoPreferences
import br.com.alex.orgs.ui.recycleView.adapter.ListaProdutoAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "ListaProdutosActivity"

class ListaProdutosActivity : AppCompatActivity(R.layout.activity_lista_produtos) {

    private val adapter = ListaProdutoAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosBinding
            .inflate(layoutInflater)
    }
    private val usuarioDao by lazy {
        AppDatabase.instacia(this)
            .usuarioDao()
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
        lifecycleScope.launch {
            launch {
                produtoDao.buscaTodos().collect { produtos ->
                    adapter.atualiza(produtos)
                }
            }

            launch{
                dataStore.data.collect { preferences ->
                    preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                        launch {
                            usuarioDao.buscaPorID(usuarioId).collect {
                                Log.i("ListaProdutos", "onCreate: $it")
                            }
                        }
                    } ?: vaiParaLogin()

                }
            }


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_lista_produtos_ordenar ->{
                lifecycleScope.launch{
                    dataStore.edit { preferences ->
                        preferences.remove(usuarioLogadoPreferences)
                    }
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

    private fun vaiParaLogin(){
        vaiPara(LoginActivity::class.java)
        finish()
    }
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
