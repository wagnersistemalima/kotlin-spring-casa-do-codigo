package br.com.wagner.casadocodigo.novoPais.model

import javax.persistence.*

@Entity
class Pais(

    @field:Column(unique = true)
    val nome: String
){
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
