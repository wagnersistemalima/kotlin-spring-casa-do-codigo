package br.com.wagner.casadocodigo.novoCliente.request

import br.com.wagner.casadocodigo.novoCliente.model.Endereco
import javax.validation.constraints.NotBlank

data class EnderecoRequest(

    @field:NotBlank
    val logradouro: String,

    @field:NotBlank
    val bairro: String,

    @field:NotBlank
    val complemento: String,

    @field:NotBlank
    val cidade: String,

    @field:NotBlank
    val cep: String,

    @field:NotBlank
    val telefone: String
){
    // metodo para converter a requisição em entidade

    fun toModel(): Endereco {
        return Endereco(logradouro, bairro, complemento, cidade, cep, telefone)
    }
}
