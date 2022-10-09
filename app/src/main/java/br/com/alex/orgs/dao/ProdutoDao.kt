package br.com.alex.orgs.dao

import br.com.alex.orgs.model.Produto
import java.math.BigDecimal

class ProdutoDao {

    fun adicionar(produto: Produto) {
        produtos.add(produto)
    }

    fun buscaTodos(): List<Produto> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>()

    }
}