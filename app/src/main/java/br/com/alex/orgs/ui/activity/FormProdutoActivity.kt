package br.com.alex.orgs.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.alex.orgs.R
import br.com.alex.orgs.database.AppDatabase
import br.com.alex.orgs.databinding.ActivityFormProdutoBinding
import br.com.alex.orgs.extensions.tentaCarregarImagem
import br.com.alex.orgs.model.Produto
import br.com.alex.orgs.ui.dialog.FormImagemDialog
import java.math.BigDecimal

class FormProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormProdutoBinding.inflate(layoutInflater)

    }
    private var url: String? = null
    private var idProduto = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar Produto"
        configuraBotaoSalvar()
        binding.activityFormProdutoImage.setOnClickListener() {
            FormImagemDialog(this)
                .Mostra(url) { imagem ->
                    url = imagem
                    binding.activityFormProdutoImage.tentaCarregarImagem(url)
                }
        }
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let {
            produtoCarregado -> idProduto =  produtoCarregado.id
            title = "Alterar Produto"
            binding.activityFormProdutoImage
                .tentaCarregarImagem(produtoCarregado.image)
            binding.activityFormProdutoNome
                .setText(produtoCarregado.nome)
            binding.activityFormProdutoDescricao
                .setText(produtoCarregado.descricao)
            binding.activityFormProdutoValor
                .setText(produtoCarregado.valor.toPlainString())
        }
    }


    private fun configuraBotaoSalvar() {
        val botaoSalvar = findViewById<Button>(R.id.activity_form_produto_botao_salvar)
        val db = AppDatabase.instacia(this)
        val produtoDao = db.produtoDao()
        botaoSalvar.setOnClickListener {
            val produtoNoco = criaProduto()
            produtoDao.salva(produtoNoco)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = findViewById<EditText>(R.id.activity_form_produto_nome)
        val nome = campoNome.text.toString()
        val campoDescricao = findViewById<EditText>(R.id.activity_form_produto_descricao)
        val descricao = campoDescricao.text.toString()
        var campoValor = findViewById<EditText>(R.id.activity_form_produto_valor)
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            nome = nome,
            descricao = descricao,
            valor = valor,
            image = url
        )
    }

}