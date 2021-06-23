package br.com.wagner.casadocodigo

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.novoCliente.request.EnderecoRequest
import br.com.wagner.casadocodigo.novoCliente.request.NovoClienteRequest
import br.com.wagner.casadocodigo.novoEstado.model.Estado
import br.com.wagner.casadocodigo.novoEstado.repository.EstadoRepository
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

@ExtendWith(SpringExtension::class)
class NovoClienteRequestTest {

    @field:Mock
    lateinit var paisRepository: PaisRepository

    @field:Mock
    lateinit var estadoRepository: EstadoRepository


    // 1 cenario de teste / caminho feliz

    @Test
    @DisplayName("deve converter dados da request em entidade")
    fun deveConverterRequestEmEntidade() {
        // cenario

        val enderecoRequeste = EnderecoRequest(
            logradouro = "rua das flores",
            bairro = "Catole",
            complemento = "perto da padaria",
            cidade = "Campina grande",
            cep = "58410505",
            telefone = "83993809934"
        )

        val request = NovoClienteRequest(
            email = "teste@gmail.com",
            nome = "teste",
            sobreNome = "silva",
            documento = "04394450438",
            idPais = 1L,
            idEstado = 1L,
            endereco = enderecoRequeste
        )

        val pais = Optional.of(Pais(nome = "Brasil"))

        val estado = Optional.of(Estado(nome = "Paraiba", pais = pais.get()))

        //ação

        Mockito.`when`(paisRepository.findById(Mockito.anyLong())).thenReturn(pais)

        Mockito.`when`(estadoRepository.findById(Mockito.anyLong())).thenReturn(estado)

        //assertivas -> nao deve lançar exception

        Assertions.assertDoesNotThrow { request.toModel(paisRepository, estadoRepository) }

    }

    // 2 cenario de teste

    @Test
    @DisplayName("deve lançar exception quando id do pais não existir")
    fun deveLançarException400QuandoIdPaisInexistente() {
        // cenario

        val enderecoRequeste = EnderecoRequest(
            logradouro = "rua das flores",
            bairro = "Catole",
            complemento = "perto da padaria",
            cidade = "Campina grande",
            cep = "58410505",
            telefone = "83993809934"
        )

        val request = NovoClienteRequest(
            email = "teste@gmail.com",
            nome = "teste",
            sobreNome = "silva",
            documento = "04394450438",
            idPais = 1L,
            idEstado = 1L,
            endereco = enderecoRequeste
        )

        val pais = Optional.empty<Pais>()   // findById volta vazio

        val paisExistente = Optional.of(Pais("Bulgaria"))

        val estado = Optional.of(Estado(nome = "Paraiba", pais = paisExistente.get()))

        //ação

        Mockito.`when`(paisRepository.findById(Mockito.anyLong())).thenReturn(pais)

        Mockito.`when`(estadoRepository.findById(Mockito.anyLong())).thenReturn(estado)

        //assertivas -> nao deve lançar exception

        Assertions.assertThrows(ExceptionGenericValidated::class.java) { request.toModel(paisRepository, estadoRepository)}

    }

    // 3 cenario de teste

    @Test
    @DisplayName("deve lançar exception quando id do estado não existir")
    fun deveLançarException400QuandoIdEstadoInexistente() {
        // cenario

        val enderecoRequeste = EnderecoRequest(
            logradouro = "rua das flores",
            bairro = "Catole",
            complemento = "perto da padaria",
            cidade = "Campina grande",
            cep = "58410505",
            telefone = "83993809934"
        )

        val request = NovoClienteRequest(
            email = "teste@gmail.com",
            nome = "teste",
            sobreNome = "silva",
            documento = "04394450438",
            idPais = 1L,
            idEstado = 1L,
            endereco = enderecoRequeste
        )

        val pais = Optional.of(Pais("Bulgaria"))   // findById volta vazio

        val estado = Optional.empty<Estado>()

        //ação

        Mockito.`when`(paisRepository.findById(Mockito.anyLong())).thenReturn(pais)

        Mockito.`when`(estadoRepository.findById(Mockito.anyLong())).thenReturn(estado)

        //assertivas -> nao deve lançar exception

        Assertions.assertThrows(ExceptionGenericValidated::class.java) { request.toModel(paisRepository, estadoRepository)}

    }
}