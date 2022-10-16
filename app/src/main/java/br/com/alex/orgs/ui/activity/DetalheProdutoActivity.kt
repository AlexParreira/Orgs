package br.com.alex.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alex.orgs.R
import br.com.alex.orgs.R.*
import br.com.alex.orgs.database.AppDatabase
import br.com.alex.orgs.database.AppDatabase_Impl
import br.com.alex.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alex.orgs.extensions.tentaCarregarImagem
import br.com.alex.orgs.model.Produto
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira

class DetalheProdutoActivity : AppCompatActivity() {

    private lateinit var produto: Produto
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(::produto.isInitialized){
            val db = AppDatabase.instacia(this)
            val produtoDao = db.produtoDao()
            when(item.itemId){
                R.id.menu_detalhe_remover->{
                    produtoDao.remove(produto)
                    finish()
                }
                R.id.menu_detalhe_editar ->{
                    Intent(this, FormProdutoActivity::class.java).apply {
                        putExtra(CHAVE_PRODUTO, produto)
                        startActivity(this)
                    }
                }
            }
        }



        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }
    private fun tentaCarregarProduto() {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            produto =produtoCarregado
            preencheCampos(produtoCarregado)
        } ?: finish()
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            activityDetalhesProdutoImagem.tentaCarregarImagem(produtoCarregado.image)
            activityDetalhesProdutoNome.text = produtoCarregado.nome
            activityDetalhesProdutoDescricao.text = produtoCarregado.descricao
            activityDetalhesProdutoValor.text =
                produtoCarregado.valor.formataParaMoedaBrasileira()
        }
    }

}