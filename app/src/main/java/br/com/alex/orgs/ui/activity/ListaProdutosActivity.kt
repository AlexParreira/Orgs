package br.com.alex.orgs.ui.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.alex.orgs.R
import br.com.alex.orgs.dao.ProdutoDao
import br.com.alex.orgs.databinding.ActivityListaProdutosBinding
import br.com.alex.orgs.databinding.FormularioImagemBinding
import br.com.alex.orgs.ui.recycleView.adapter.ListaProdutoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


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

    }
    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
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
    }
}