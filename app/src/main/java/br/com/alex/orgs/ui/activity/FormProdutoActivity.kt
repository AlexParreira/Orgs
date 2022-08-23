package br.com.alex.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import br.com.alex.orgs.R
import br.com.alex.orgs.dao.ProdutoDao
import br.com.alex.orgs.model.Produto
import java.math.BigDecimal

class FormProdutoActivity :
    AppCompatActivity(R.layout.activity_form_produto) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configuraBotaoSalvar()

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
            valor = valor
        )
    }

}