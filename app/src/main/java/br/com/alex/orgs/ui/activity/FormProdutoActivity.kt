package br.com.alex.orgs.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import br.com.alex.orgs.database.AppDatabase
import br.com.alex.orgs.database.dao.ProdutoDao
import br.com.alex.orgs.databinding.ActivityFormProdutoBinding
import br.com.alex.orgs.extensions.tentaCarregarImagem
import br.com.alex.orgs.model.Produto
import br.com.alex.orgs.ui.dialog.FormImagemDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FormProdutoActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityFormProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var produtoId = 0L
    private val produtoDao: ProdutoDao by lazy {
        val db = AppDatabase.instacia(this)
        db.produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configuraBotaoSalvar()
        binding.activityFormProdutoImage.setOnClickListener {
            FormImagemDialog(this)
                .Mostra(url) { imagem ->
                    url = imagem
                    binding.activityFormProdutoImage.tentaCarregarImagem(url)
                }
        }
        tentaCarregarProduto()
        lifecycleScope.launch{
            usuario
            .filterNotNull()
            .collect{
                Log.i("FormularioProduto","onCreate: $it")
            }
        }
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    override fun onResume() {
        super.onResume()
        tentaBuscarProduto()
    }

    private fun tentaBuscarProduto() {
        lifecycle.coroutineScope.launch {
            produtoDao.buscarPorId(produtoId).collect {
                it?.let { produtoEncontrado ->
                    title = "Alterar produto"
                    preencheCampos(produtoEncontrado)
                }
            }
        }
    }

    private fun preencheCampos(produto: Produto) {
        url = produto.image
        binding.activityFormProdutoImage
            .tentaCarregarImagem(produto.image)
        binding.activityFormProdutoNome
            .setText(produto.nome)
        binding.activityFormProdutoDescricao
            .setText(produto.descricao)
        binding.activityFormProdutoValor
            .setText(produto.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormProdutoBotaoSalvar

        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            lifecycle.coroutineScope.launch {
                produtoDao.salva(produtoNovo)
                finish()
            }
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.activityFormProdutoNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.activityFormProdutoDescricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.activityFormProdutoValor
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            image = url
        )
    }
}