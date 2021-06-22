package br.com.wagner.casadocodigo.novoPais.repository

import br.com.wagner.casadocodigo.novoPais.model.Pais
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaisRepository: JpaRepository<Pais, Long> {

    fun existsByNome(nome: String): Boolean
}