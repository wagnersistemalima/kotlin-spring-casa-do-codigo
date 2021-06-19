package br.com.wagner.casadocodigo.novoAutor.request

import br.com.wagner.casadocodigo.novoAutor.model.Autor
import com.fasterxml.jackson.annotation.JsonCreator
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class NovoAutorRequest (

    @field:NotBlank
    val nome: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    @field:Size(max = 400)
    val descricao: String

    ) {

    // metodo para converter requisição em entidade

    fun toModel(): Autor {
        return Autor(nome, email, descricao)
    }
}
