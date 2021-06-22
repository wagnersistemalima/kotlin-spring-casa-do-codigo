package br.com.wagner.casadocodigo.novoEstado.model

import br.com.wagner.casadocodigo.novoPais.model.Pais
import javax.persistence.*

@Entity
class Estado(

    val nome: String,

    @ManyToOne
    val pais: Pais
){
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
