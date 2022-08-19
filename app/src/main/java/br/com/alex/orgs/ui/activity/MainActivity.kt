package br.com.alex.orgs.ui.activity


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alex.orgs.R
import br.com.alex.orgs.model.Produto
import br.com.alex.orgs.ui.recycleView.adapter.ListaProdutoAdapter
import java.math.BigDecimal


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //   val nome = findViewById<TextView>(R.id.nome)
        //   nome.text = "Cesta de frutas"
        //   val descricao = findViewById<TextView>(R.id.descricao)
        //   descricao.text = "Laranja, manga e uva"
        //   val valor = findViewById<TextView>(R.id.valor)
        //   valor.text = "19.9"
        val recyclerView = findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.adapter = ListaProdutoAdapter(
            context = this, produtos = listOf(
                Produto(
                    nome = "teste",
                    descricao = "teste desc",
                    valor = BigDecimal("19.99")
                ),
                Produto(
                    nome = "teste2",
                    descricao = "teste desc2",
                    valor = BigDecimal("29.99")
                ),
            )
        )

    }
}