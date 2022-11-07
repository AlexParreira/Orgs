package br.com.alex.orgs.ui.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.alex.orgs.R
import br.com.alex.orgs.database.AppDatabase
import br.com.alex.orgs.databinding.ActivityListaProdutosBinding
import br.com.alex.orgs.ui.recycleView.adapter.ListaProdutoAdapter

private const val TAG = "ListaProdutosActivity"
class ListaProdutosActivity : AppCompatActivity(R.layout.activity_lista_produtos) {

    private val adapter = ListaProdutoAdapter(context = this)

    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecicleView()
        configuraFab()


    }

    override fun onResume() {
        val db = AppDatabase.instacia(this)
        val produtoDao = db.produtoDao()
        super.onResume()
        adapter.atualiza(produtoDao.buscaTodos())
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
            Log.i(TAG,"configuraRecyclerView: Editar $it")
        }
        adapter.quandoClicarEmRemover = {
            Log.i(TAG,"configuraRecyclerView: Remover $it")
        }
    }
}
