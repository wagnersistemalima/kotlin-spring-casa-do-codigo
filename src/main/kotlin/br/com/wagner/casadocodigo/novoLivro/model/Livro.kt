package br.com.wagner.casadocodigo.novoLivro.model

import br.com.wagner.casadocodigo.novaCategoria.model.Categoria
import br.com.wagner.casadocodigo.novoAutor.model.Autor
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Entity
class Livro(

    @field:Column(unique = true)
    val titulo: String,

    val resumo: String,

    val sumario: String,

    val preco: BigDecimal,

    val numeroPagina: Int,

    val isbn: String,

    val dataPublicacao: LocalDate,

    @field:OneToOne
    val categoria: Categoria,

    @field:OneToOne
    val autor: Autor
){

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null;
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Livro

        if (titulo != other.titulo) return false

        return true
    }

    override fun hashCode(): Int {
        return titulo.hashCode()
    }


}
