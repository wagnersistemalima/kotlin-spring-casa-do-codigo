package br.com.wagner.casadocodigo.novaCategoria.request

import br.com.wagner.casadocodigo.novaCategoria.model.Categoria
import javax.validation.constraints.NotBlank

data class NovaCategoriaRequest(

    @field:NotBlank
    val nome: String
) {

    // metodo para converter a requisição em entidade

    fun toModel(): Categoria {
        return Categoria(nome)
    }
}
