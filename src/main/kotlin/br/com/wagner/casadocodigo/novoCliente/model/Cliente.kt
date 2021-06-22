package br.com.wagner.casadocodigo.novoCliente.model

import br.com.wagner.casadocodigo.novoCliente.request.EnderecoRequest
import br.com.wagner.casadocodigo.novoEstado.model.Estado
import br.com.wagner.casadocodigo.novoPais.model.Pais
import javax.persistence.*

@Entity
class Cliente(

    val email: String,
    val nome: String,
    val sobreNome: String,
    val documento: String,

    @ManyToOne
    val pais: Pais,

    @ManyToOne
    val estado: Estado,

    @Embedded                           // incorporar para a mesma tabela o endereço
    val endereco: Endereco
){

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
