package br.com.wagner.casadocodigo.novoLivro.repository

import br.com.wagner.casadocodigo.novoLivro.model.Livro
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LivroRepository: JpaRepository<Livro, Long> {

    fun existsByTitulo(titulo: String): Boolean

    fun existsByIsbn(isbn: String): Boolean
}