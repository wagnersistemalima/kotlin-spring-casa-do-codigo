package br.com.wagner.casadocodigo.novoAutor.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Autor(

    val nome: String,
    val email: String,
    val descricao: String
){

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val dataRegistro: LocalDateTime = LocalDateTime.now()
}
