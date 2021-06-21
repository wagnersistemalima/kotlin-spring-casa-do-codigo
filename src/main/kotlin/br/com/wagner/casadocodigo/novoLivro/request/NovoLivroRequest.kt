package br.com.wagner.casadocodigo.novoLivro.request

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository
import br.com.wagner.casadocodigo.novoAutor.model.Autor
import br.com.wagner.casadocodigo.novoAutor.repository.AutorRepository
import br.com.wagner.casadocodigo.novoLivro.model.Livro
import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.*

data class NovoLivroRequest(

    @field:NotBlank
    val titulo: String,

    @field:NotBlank
    @field:Size(max = 500)
    val resumo: String,

    val sumario: String,

    @field:NotNull
    @field:Min(20)
    val preco: BigDecimal,

    @field:NotNull
    @field:Min(100)
    val numeroPagina: Int,

    @field:NotBlank
    val isbn: String,

    @field:Future
    @field:JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    val dataPublicacao: LocalDate,

    @field:NotNull
    val idCategoria: Long,

    @field:NotNull
    val idAutor: Long

) {

    // metodo para converter dados da requisição em entidade

    fun toModel(autorRepository: AutorRepository, categoriaRepository: CategoriaRepository): Livro {

       val possivelAutor = autorRepository.findById(idAutor)

        val possivelCategoria = categoriaRepository.findById(idCategoria)

        if(possivelAutor.isEmpty) {
            throw  ExceptionGenericValidated("id do autor não existe")
        }
        else if(possivelCategoria.isEmpty) {
            throw  ExceptionGenericValidated("id da categoria não existe")
        }
        else {

            val autor = possivelAutor.get()
            val categoria =  possivelCategoria.get()

            return Livro(titulo, resumo, sumario, preco, numeroPagina, isbn, dataPublicacao, categoria, autor)

        }

    }

}


