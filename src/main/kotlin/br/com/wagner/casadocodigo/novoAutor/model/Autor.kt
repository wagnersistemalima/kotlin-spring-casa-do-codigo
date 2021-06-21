package br.com.wagner.casadocodigo.novoAutor.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Autor(

    val nome: String,

    @field:Column(unique = true)
    val email: String,

    val descricao: String
){

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val dataRegistro: LocalDateTime = LocalDateTime.now()
}
