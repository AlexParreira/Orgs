package br.com.alex.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import br.com.alex.orgs.databinding.FormularioImagemBinding
import br.com.alex.orgs.extensions.tentaCarregarImagem

class FormImagemDialog(private val context:Context ) {
    fun Mostra(quandoImagemCarregada: (imagem:String)-> Unit){
        val binding = FormularioImagemBinding
            .inflate(LayoutInflater.from(context))
        binding.formularioImagemBotaoCarregar.setOnClickListener() {
            val url = binding.formularioImagemUrl.text.toString()
            binding.formularioImagemImageView.tentaCarregarImagem(url)
        }
        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("Confirmar") { _, _ ->
                val url = binding.formularioImagemUrl.text.toString()
                quandoImagemCarregada(url)

            }
            .setNegativeButton("Cancelar") { _, _ ->

            }
            .show()
    }
}