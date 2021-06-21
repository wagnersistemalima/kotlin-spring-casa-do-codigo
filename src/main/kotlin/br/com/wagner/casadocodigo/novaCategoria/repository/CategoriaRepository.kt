package br.com.wagner.casadocodigo.novaCategoria.repository

import br.com.wagner.casadocodigo.novaCategoria.model.Categoria
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoriaRepository: JpaRepository<Categoria, Long> {

    fun existsByNome(nome: String): Boolean
}