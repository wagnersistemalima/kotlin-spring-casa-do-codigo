package br.com.wagner.casadocodigo

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.novoEstado.request.NovoEstadoRequest
import br.com.wagner.casadocodigo.novoPais.model.Pais
import br.com.wagner.casadocodigo.novoPais.repository.PaisRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

// teste de unidade


@ExtendWith(SpringExtension::class)             // nao carrega o contexto
class NovoEstadoRequestTest {

    @field:Mock              // objeto dependente/ simular o comportamento
    lateinit var paisRepository: PaisRepository

    // 1 cenario de teste / caminho feliz

    @Test
    @DisplayName("deve converter a requisição en entidade")
    fun toModelDeveConverterRequestEmEntidade() {
        // cenario

        val request = NovoEstadoRequest(nome = "Paraiba", idPais = 1L)

        val pais = Optional.of(Pais(request.nome)) // findById -> retorna um possivel pais

        /// ação

        Mockito.`when`(paisRepository.findById(Mockito.anyLong())).thenReturn(pais) // comportamento do repository

        //assertivas -> não deve lançar exceptions

        Assertions.assertDoesNotThrow {  request.toModel(paisRepository) } /// nao vai lançar exception quando converter request em entidade

    }

    // 2 cenario de teste

    @Test
    @DisplayName("deve lançar exception")
    fun toModelDeveRetornarException400() {
        // cenario

        val request = NovoEstadoRequest("Paraiba", 5000)

        val pais = Optional.empty<Pais>()  // findById retorna pode retornar vazio, caso pais nao exista

        /// ação

        Mockito.`when`(paisRepository.findById(Mockito.anyLong())).thenReturn(pais) // comportamento do repository

        //assertivas / deve lançar exceção

        Assertions.assertThrows(ExceptionGenericValidated::class.java) {  request.toModel(paisRepository) }

    }
}