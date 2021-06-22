package br.com.wagner.casadocodigo.novoEstado.repository

import br.com.wagner.casadocodigo.novoEstado.model.Estado
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EstadoRepository: JpaRepository<Estado, Long> {

    fun existsByNomeAndPais_Id(nome: String, id: Long): Boolean

}