package br.com.alex.orgs.ui.recycleView.adapter

import android.content.Context
import android.icu.text.NumberFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.com.alex.orgs.R
import br.com.alex.orgs.model.Produto
import br.com.alex.orgs.databinding.ProdutoItemBinding
import coil.load
import java.util.*

class ListaProdutoAdapter(
    private val context: Context,
    produtos: List<Produto>
) : RecyclerView.Adapter<ListaProdutoAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.N)
        fun vincula(produto: Produto) {
            val nome = binding.produtoItemNome
            nome.text = produto.nome
            val descricao = binding.produtoItemDescricao
            descricao.text =  produto.descricao
            val valor =  binding.produtoItemValor
            val formatador: NumberFormat = NumberFormat
                .getCurrencyInstance(Locale("pt","br"))
            val valorEmMoeda = formatador.format(produto.valor)
            valor.text = valorEmMoeda

            binding.imageView.load(produto.image){
                fallback(R.drawable.imagem_padrao)
                error(R.drawable.imagem_padrao)
            }

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
