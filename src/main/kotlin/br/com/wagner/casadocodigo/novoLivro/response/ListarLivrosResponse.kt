package br.com.wagner.casadocodigo.novoLivro.response

import br.com.wagner.casadocodigo.novoLivro.model.Livro

data class ListarLivrosResponse(
    var id: Long?,
    val titulo: String?
){

    constructor(livro: Livro): this(livro.id, livro.titulo)


}
