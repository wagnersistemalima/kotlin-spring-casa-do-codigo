package br.com.wagner.casadocodigo

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.novaCategoria.model.Categoria
import br.com.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository
import br.com.wagner.casadocodigo.novoAutor.model.Autor
import br.com.wagner.casadocodigo.novoAutor.repository.AutorRepository
import br.com.wagner.casadocodigo.novoLivro.request.NovoLivroRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

// teste de unidade

@ExtendWith(SpringExtension::class)        // não carrega o contexro
class NovoLivroRequestTest {

    @field:Mock
    lateinit var autorRepository: AutorRepository

    @field:Mock
    lateinit var categoriaRepository: CategoriaRepository

    // 1 cenario de teste/ caminho feliz

    @Test
    @DisplayName("deve converter requisiçao em entidade")
    fun deveConverterRequestEmEntidade() {

        // cenario

        val autor = Optional.of(Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        ))

        val categoria = Optional.of(Categoria(nome = "Romance"))

        val request = NovoLivroRequest(
            titulo = "Livro bom",
            resumo = "bem compacto",
            sumario = "12345",
            preco = BigDecimal(55.0),
            numeroPagina = 250,
            isbn = "oiuygt",
            dataPublicacao = LocalDate.now().plusDays(1L),
            idCategoria = 1,
            idAutor = 1
        )

        //ação

        Mockito.`when`(autorRepository.findById(Mockito.anyLong())).thenReturn(autor)

        Mockito.`when`(categoriaRepository.findById(Mockito.anyLong())).thenReturn(categoria)

        //assertivas

        Assertions.assertDoesNotThrow { request.toModel(autorRepository, categoriaRepository) }
    }

    // 2 cenario de testes

    @Test
    @DisplayName("deve retornar exception, quando id autor não existir")
    fun deveRetornar400ExceptionQuandoiIdAutorInexistente() {

        // cenario

        val autor = Optional.empty<Autor>()   // findById volta vazio

        val categoria = Optional.of(Categoria(nome = "Romance"))

        val request = NovoLivroRequest(
            titulo = "Livro bom",
            resumo = "bem compacto",
            sumario = "12345",
            preco = BigDecimal(55.0),
            numeroPagina = 250,
            isbn = "oiuygt",
            dataPublicacao = LocalDate.now().plusDays(1L),
            idCategoria = 1,
            idAutor = 1
        )

        //ação

        Mockito.`when`(autorRepository.findById(Mockito.anyLong())).thenReturn(autor)

        Mockito.`when`(categoriaRepository.findById(Mockito.anyLong())).thenReturn(categoria)

        //assertivas

        Assertions.assertThrows(ExceptionGenericValidated::class.java) { request.toModel(autorRepository, categoriaRepository)}
    }

    // 3 cenario de testes

    @Test
    @DisplayName("deve retornar exception, quando id categoria não existir")
    fun deveRetornar400ExceptionQuandoiIdCategoriaInexistente() {

        // cenario

        val autor = Optional.of(Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        ))

        val categoria = Optional.empty<Categoria>()   // findById volta vazio

        val request = NovoLivroRequest(
            titulo = "Livro bom",
            resumo = "bem compacto",
            sumario = "12345",
            preco = BigDecimal(55.0),
            numeroPagina = 250,
            isbn = "oiuygt",
            dataPublicacao = LocalDate.now().plusDays(1L),
            idCategoria = 1,
            idAutor = 1
        )

        //ação

        Mockito.`when`(autorRepository.findById(Mockito.anyLong())).thenReturn(autor)

        Mockito.`when`(categoriaRepository.findById(Mockito.anyLong())).thenReturn(categoria)

        //assertivas

        Assertions.assertThrows(ExceptionGenericValidated::class.java) { request.toModel(autorRepository, categoriaRepository)}
    }
}