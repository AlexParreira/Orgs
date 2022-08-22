package br.com.alex.orgs.ui.activity


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alex.orgs.R
import br.com.alex.orgs.model.Produto
import br.com.alex.orgs.ui.recycleView.adapter.ListaProdutoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.math.BigDecimal


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener{
            val intent = Intent(this, FormProdutoActivity::class.java)
            startActivity(intent)
        }
    }
}