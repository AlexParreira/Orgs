package br.com.alex.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import br.com.alex.orgs.R
import br.com.alex.orgs.dao.ProdutoDao
import br.com.alex.orgs.model.Produto
import java.math.BigDecimal
import br.com.alex.orgs.databinding.ActivityFormProdutoBinding
import br.com.alex.orgs.databinding.FormularioImagemBinding
import br.com.alex.orgs.extensions.tentaCarregarImagem
import br.com.alex.orgs.ui.dialog.FormImagemDialog
import coil.load

class FormProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormProdutoBinding.inflate(layoutInflater)

    }
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        binding.activityFormProdutoImage.setOnClickListener() {
            FormImagemDialog(this)
                .Mostra(url){imagem ->
                    url = imagem
                    binding.activityFormProdutoImage.tentaCarregarImagem(url)
                }
        }
    }


    private fun configuraBotaoSalvar() {
        val botaoSalvar = findViewById<Button>(R.id.activity_form_produto_botao_salvar)
        val dao = ProdutoDao()
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            dao.adicionar(produtoNovo)
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