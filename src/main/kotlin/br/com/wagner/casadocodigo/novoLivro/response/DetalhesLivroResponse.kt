package br.com.wagner.casadocodigo.novoLivro.response

import br.com.wagner.casadocodigo.novoLivro.model.Livro
import java.math.BigDecimal
import java.time.LocalDate

class DetalhesLivroResponse(

    val idLivro: Long?,

    val titulo: String,

    val resumo: String,

    val sumario: String,

    val preco: BigDecimal,

    val numeroPagina: Int,

    val isbn: String,

    val dataPublicacao: LocalDate,

    val nomeCategoria: String,

    val nomeAutor: String,

    val descricaoAutor: String
){
    constructor(livro: Livro): this(
        livro.id,
        livro.titulo,
        livro.resumo,
        livro.sumario,
        livro.preco,
        livro.numeroPagina,
        livro.isbn,
        livro.dataPublicacao,
        livro.categoria.nome,
        livro.autor.nome,
        livro.autor.descricao

    )
}
