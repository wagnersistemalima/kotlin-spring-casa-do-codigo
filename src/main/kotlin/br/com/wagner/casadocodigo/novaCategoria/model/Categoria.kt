package br.com.wagner.casadocodigo.novaCategoria.model

import javax.persistence.*

@Entity
class Categoria(

    @field:Column(unique = true)
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
