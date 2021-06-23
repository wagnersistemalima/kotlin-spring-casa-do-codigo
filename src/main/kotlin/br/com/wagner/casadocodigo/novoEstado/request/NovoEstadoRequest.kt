package br.com.wagner.casadocodigo.novoEstado.request

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.novoEstado.model.Estado
import br.com.wagner.casadocodigo.novoPais.model.Pais
import br.com.wagner.casadocodigo.novoPais.repository.PaisRepository
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class NovoEstadoRequest(

    @field:NotBlank
    val nome: String,

    @NotNull
    val idPais: Long
) {


    // metodo para converter requisição em entidade

    fun toModel(paisRepository: PaisRepository): Estado {

        val possivelPais = paisRepository.findById(idPais)

        // validação

        if(possivelPais.isEmpty) {
            throw ExceptionGenericValidated("id do pais informado não existe")
        }

        val pais = possivelPais.get()

        return Estado(nome, pais)
    }
}