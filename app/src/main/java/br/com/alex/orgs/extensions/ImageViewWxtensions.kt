package br.com.alex.orgs.extensions

import android.widget.ImageView
import br.com.alex.orgs.R
import coil.load


fun ImageView.tentaCarregarImagem(
    url: String? = null,
    fallback: Int = R.drawable.imagem_padrao
) {
    load(url) {
        fallback(fallback)
        error(br.com.alex.orgs.R.drawable.imagem_padrao)
        placeholder(br.com.alex.orgs.R.drawable.placeholder)
    }
}