package br.com.wagner.casadocodigo.novoCliente.repository

import br.com.wagner.casadocodigo.novoCliente.model.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteRepository: JpaRepository<Cliente, Long> {

    fun existsByEmail(email: String): Boolean

    fun existsByDocumento(documento: String): Boolean
}