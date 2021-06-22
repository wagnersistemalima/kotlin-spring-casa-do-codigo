package br.com.wagner.casadocodigo.novoCliente.model

import javax.persistence.Embeddable

@Embeddable               // classe sera incorporada pela entidade cliente
class Endereco(

    val logradouro: String,

    val bairro: String,

    val complemento: String,

    val cidade: String,

    val cep: String,

    val telefone: String
)
