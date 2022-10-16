package br.com.alex.orgs.ui.recycleView.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.com.alex.orgs.R
import br.com.alex.orgs.databinding.ProdutoItemBinding
import br.com.alex.orgs.extensions.tentaCarregarImagem
import br.com.alex.orgs.model.Produto
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira

class ListaProdutoAdapter(
    private val context: Context,
    produtos: List<Produto> = emptyList(),
    var quandoClicaNoItem: (produto: Produto) -> Unit = {},
    var quandoClicarEmEditar: (produto: Produto) -> Unit = {},
    var quandoClicarEmRemover: (produto: Produto) -> Unit = {}
) : RecyclerView.Adapter<ListaProdutoAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    inner class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {
        private lateinit var produto: Produto


        init {
            itemView.setOnClickListener {
                if (::produto.isInitialized) {
                    quandoClicaNoItem(produto)
                }
            }
            itemView.setOnLongClickListener {
                PopupMenu(context, itemView).apply {
                    menuInflater.inflate(
                        R.menu.menu_detalhes_produtos, menu
                    )
                    setOnMenuItemClickListener(this@ViewHolder)
                }.show()
                true
            }


        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun vincula(produto: Produto) {
            this.produto = produto
            val nome = binding.produtoItemNome
            nome.text = produto.nome
            val descricao = binding.produtoItemDescricao
            descricao.text = produto.descricao
            val valor = binding.produtoItemValor
            val valorEmMoeda: String = produto.valor
                .formataParaMoedaBrasileira()
            valor.text = valorEmMoeda
            val visibilidade = if (produto.image != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.imageView.tentaCarregarImagem(produto.image)

        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            item?.let {
                when (it.itemId) {
                    R.id.menu_detalhe_editar -> {
                        quandoClicarEmEditar(produto)
                    }
                    R.id.menu_detalhe_remover -> {
                        quandoClicarEmRemover(produto)
                    }
                }
            }
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProdutoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    override fun getItemCount(): Int = produtos.size
    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }

}
