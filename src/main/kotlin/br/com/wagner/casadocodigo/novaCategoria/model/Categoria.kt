package br.com.wagner.casadocodigo.novaCategoria.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Categoria(

    val nome: String
){

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Categoria

        if (nome != other.nome) return false

        return true
    }

    override fun hashCode(): Int {
        return nome.hashCode()
    }


}
