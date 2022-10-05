package br.com.alex.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.alex.orgs.databinding.FormularioImagemBinding
import br.com.alex.orgs.extensions.tentaCarregarImagem

class FormImagemDialog(private val context:Context ) {
    fun Mostra(urlPadrao: String? = null, quandoImagemCarregada: (imagem:String)-> Unit){
        FormularioImagemBinding
            .inflate(LayoutInflater.from(context)).apply {
                urlPadrao?.let {
                    formularioImagemImageView.tentaCarregarImagem(it)
                    formularioImagemUrl.setText(it)

                }
                formularioImagemBotaoCarregar.setOnClickListener() {
                    val url =formularioImagemUrl.text.toString()
                    formularioImagemImageView.tentaCarregarImagem(url)
                }
                AlertDialog.Builder(context)
                    .setView(root)
                    .setPositiveButton("Confirmar") { _, _ ->
                        val url = formularioImagemUrl.text.toString()
                        quandoImagemCarregada(url)

                    }
                    .setNegativeButton("Cancelar") { _, _ ->

                    }
                    .show()
            }





    }
}