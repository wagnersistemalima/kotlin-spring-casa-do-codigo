package br.com.wagner.casadocodigo.novoAutor.repository

import br.com.wagner.casadocodigo.novoAutor.model.Autor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AutorRepository : JpaRepository<Autor, Long>{


    fun existsByEmail(email: String): Boolean

}