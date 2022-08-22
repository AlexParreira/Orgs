package br.com.alex.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import br.com.alex.orgs.R
import br.com.alex.orgs.model.Produto
import java.math.BigDecimal

class FormProdutoActivity :
    AppCompatActivity(R.layout.activity_form_produto) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val botaoSalvar = findViewById<Button>(R.id.botao_salvar)
        botaoSalvar.setOnClickListener {
            val campoNome = findViewById<EditText>(R.id.nome)
            val nome = campoNome.text.toString()
            val campoDescricao = findViewById<EditText>(R.id.descricao)
            val descricao = campoDescricao.text.toString()
            var campoValor = findViewById<EditText>(R.id.valor)
            val valorEmTexto = campoValor.text.toString()
            val valor = if(valorEmTexto.isBlank()){
                 BigDecimal.ZERO
             }else{
                 BigDecimal(valorEmTexto)
             }
            val produtoNovo = Produto(
                nome=nome,
                descricao=descricao,
                valor=valor
            )

            Log.i("FormProdutoActivity", "onCreate: $produtoNovo") // <- Teste Logcat
        }

    }

}