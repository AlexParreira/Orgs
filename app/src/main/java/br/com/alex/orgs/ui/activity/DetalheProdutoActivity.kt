package br.com.alex.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import br.com.alex.orgs.R
import br.com.alex.orgs.dao.ProdutoDao
import br.com.alex.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alex.orgs.model.Produto
import java.math.BigDecimal
import br.com.alex.orgs.databinding.ActivityFormProdutoBinding
import br.com.alex.orgs.databinding.FormularioImagemBinding
import br.com.alex.orgs.extensions.tentaCarregarImagem
import br.com.alex.orgs.ui.dialog.FormImagemDialog
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira
import coil.load

class DetalheProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    private fun tentaCarregarProduto() {
        // tentativa de buscar o produto se ele existir,
        // caso contrário, finalizar a Activity
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
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