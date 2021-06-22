package br.com.wagner.casadocodigo

import br.com.wagner.casadocodigo.novaCategoria.model.Categoria
import br.com.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository
import br.com.wagner.casadocodigo.novoAutor.model.Autor
import br.com.wagner.casadocodigo.novoAutor.repository.AutorRepository
import br.com.wagner.casadocodigo.novoLivro.model.Livro
import br.com.wagner.casadocodigo.novoLivro.repository.LivroRepository
import br.com.wagner.casadocodigo.novoLivro.response.ListarLivrosResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.net.URI
import java.time.LocalDate
import java.util.stream.Stream

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@Transactional
class ListarLivrosControllerTest {

    @field:Autowired
    lateinit var livroRepository: LivroRepository

    @field:Autowired
    lateinit var autorRepository: AutorRepository

    @field:Autowired
    lateinit var categoriaRepository: CategoriaRepository

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    // rodar antes de cada teste


    // rodar depois de cada teste
    @AfterEach
    internal fun tearDown() {
        livroRepository.deleteAll()
        autorRepository.deleteAll()
        categoriaRepository.deleteAll()
    }

    // 1 cenario de teste / caminho feliz

    @Test
    @DisplayName("deve retornar 200, com uma lista de livros")
    fun deveRetornar200ComListaDeLivros() {
        // cenario

        val autor = Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        )
        autorRepository.save(autor)

        val categoria = Categoria(nome = "Romance")
        categoriaRepository.save(categoria)

        val livro1 = Livro(
            titulo = "O homem de ferro",
            resumo = "livro ação",
            sumario = "explicativo",
            preco = BigDecimal(50.0),
            numeroPagina = 200,
            isbn = "uytrtg",
            dataPublicacao = LocalDate.now().plusDays(1L),
            categoria = categoria,
            autor = autor
        )
        livroRepository.save(livro1)

        val livro2 = Livro(
            titulo = "O homem da mascara",
            resumo = "livro ação",
            sumario = "explicativo",
            preco = BigDecimal(50.0),
            numeroPagina = 200,
            isbn = "uytrtguyy",
            dataPublicacao = LocalDate.now().plusDays(1L),
            categoria = categoria,
            autor = autor
        )
        livroRepository.save(livro2)

        val uri = URI("/livros")

        val lista = livroRepository.findAll()

        val listagemResponse = lista.stream().map { livro -> ListarLivrosResponse(livro) }

        // ação

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().json(toJson(listagemResponse)))

        // assertivas
    }

    // 2 cenario de teste

    @Test
    @DisplayName("deve retornar lista vazia quando não tiver livros cadastrados")
    fun deveRetornarListaVazia() {
        // cenario

        val uri = URI("/livros")

        val lista = livroRepository.findAll()


        val listagemResponse = lista.stream().map { livro -> ListarLivrosResponse(livro) }

        // ação

        mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().json(toJson(listagemResponse)))

        // assertivas

        Assertions.assertEquals(0, lista.size);
    }

    // metodo para desserializar objeto de resposta

    fun toJson(response: Stream<ListarLivrosResponse>): String {
        return objectMapper.writeValueAsString(response)
    }
}