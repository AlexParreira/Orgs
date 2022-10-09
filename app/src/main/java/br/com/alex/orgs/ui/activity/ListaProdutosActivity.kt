package br.com.alex.orgs.ui.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import br.com.alex.orgs.R
import br.com.alex.orgs.dao.ProdutoDao
import br.com.alex.orgs.database.AppDatabase
import br.com.alex.orgs.databinding.ActivityListaProdutosBinding
import br.com.alex.orgs.databinding.FormularioImagemBinding
import br.com.alex.orgs.model.Produto
import br.com.alex.orgs.ui.recycleView.adapter.ListaProdutoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.math.BigDecimal


class ListaProdutosActivity : AppCompatActivity(R.layout.activity_lista_produtos) {

    private val dao = ProdutoDao()
    private val adapter = ListaProdutoAdapter(context = this, produtos = dao.buscaTodos())
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecicleView()
        configuraFab()
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "orgs.db"
        ).allowMainThreadQueries()
            .build()
        val produtoDao = db.produtoDao()
        produtoDao.salva(
            Produto(
                nome = "Teste",
                descricao = "Isto é um teste",
                valor = BigDecimal("10.1")
            )
        )
        adapter.atualiza(produtoDao.buscaTodos())
    }

    override fun onResume() {
        super.onResume()
        //adapter.atualiza(dao.buscaTodos())
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
                putExtra(CHAVE_PRODUTO, it)
            }
            startActivity(intent)
        }
    }
}
