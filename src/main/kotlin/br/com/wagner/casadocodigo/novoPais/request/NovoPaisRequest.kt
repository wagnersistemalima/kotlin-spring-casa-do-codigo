package br.com.wagner.casadocodigo.novoPais.request

import br.com.wagner.casadocodigo.novoPais.model.Pais
import javax.validation.constraints.NotBlank

data class NovoPaisRequest(

    @field:NotBlank
    val nome: String
) {

    // metodo para converter requisição em entidade

    fun toModel(): Pais {
        return Pais(nome)
    }
}
