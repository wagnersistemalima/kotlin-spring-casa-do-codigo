package br.com.wagner.casadocodigo.novoCliente.request

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.novoCliente.model.Cliente
import br.com.wagner.casadocodigo.novoEstado.repository.EstadoRepository
import br.com.wagner.casadocodigo.novoPais.repository.PaisRepository
import br.com.wagner.casadocodigo.validated.CpfOuCnpj
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class NovoClienteRequest(

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val nome: String,

    @field:NotBlank
    val sobreNome: String,

    @field:CpfOuCnpj
    @field:NotBlank
    val documento: String,

    @NotNull
    val idPais: Long,

    @NotNull
    val idEstado: Long,

    @NotNull
    val endereco: EnderecoRequest

) {

    // metodo para converter requisição em entidade

    fun toModel(paisRepository: PaisRepository, estadoRepository: EstadoRepository): Cliente {

        // validação

        if(!paisRepository.existsById(idPais)) {
            throw ExceptionGenericValidated("Id do pais não existe")
        }

        if(!estadoRepository.existsById(idEstado)) {
            throw  ExceptionGenericValidated("Id estado não existe")
        }

        val possivelPais = paisRepository.findById(idPais)
        val possivelEstado = estadoRepository.findById(idEstado)

        val pais = possivelPais.get()
        val estado = possivelEstado.get()

        return Cliente(email, nome, sobreNome, documento, pais, estado, endereco.toModel())

    }
}
