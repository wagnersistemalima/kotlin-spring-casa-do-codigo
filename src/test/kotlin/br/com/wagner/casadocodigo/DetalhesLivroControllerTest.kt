package br.com.wagner.casadocodigo

import br.com.wagner.casadocodigo.novaCategoria.model.Categoria
import br.com.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository
import br.com.wagner.casadocodigo.novoAutor.model.Autor
import br.com.wagner.casadocodigo.novoAutor.repository.AutorRepository
import br.com.wagner.casadocodigo.novoLivro.model.Livro
import br.com.wagner.casadocodigo.novoLivro.repository.LivroRepository
import br.com.wagner.casadocodigo.novoLivro.response.DetalhesLivroResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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
import org.springframework.web.util.UriComponentsBuilder
import java.math.BigDecimal
import java.net.URI
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@Transactional
class DetalhesLivroControllerTest {

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

    // rodar depois de cada teste

    @AfterEach
    internal fun tearDown() {
        livroRepository.deleteAll()
        autorRepository.deleteAll()
        categoriaRepository.deleteAll()
    }

    // 1 cenario de teste / caminho feliz

    @Test
    @DisplayName("deve retornar 200, com detalhes do livro")
    fun deveRetornar200DetalhesLivro() {
        // cenario

        val autor = Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        )
        autorRepository.save(autor)

        val categoria = Categoria(nome = "Romance")
        categoriaRepository.save(categoria)

        val livro = Livro(
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
        livroRepository.save(livro)

        val response = DetalhesLivroResponse(livro)

        val uri = UriComponentsBuilder.fromUriString("/livros/{id}").buildAndExpand(livro.id).toUri()

        // ação

        mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().json(toJson(response)))

    }

    // 2 cenario de teste

    @Test
    @DisplayName("deve retornar 404, quando id do livro nao encontrado")
    fun deveRetornar404() {
        // cenario

        val autor = Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        )
        autorRepository.save(autor)

        val categoria = Categoria(nome = "Romance")
        categoriaRepository.save(categoria)

        val livro = Livro(
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
        livroRepository.save(livro)


        val uri = UriComponentsBuilder.fromUriString("/livros/{id}").buildAndExpand(5000).toUri()

        // ação

        mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(404))


    }

    // metodo para desserializar objeto de resposta

    fun toJson(response: DetalhesLivroResponse): String {
        return objectMapper.writeValueAsString(response)
    }
}