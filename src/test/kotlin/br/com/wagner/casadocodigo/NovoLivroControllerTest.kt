package br.com.wagner.casadocodigo

import br.com.wagner.casadocodigo.novaCategoria.model.Categoria
import br.com.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository
import br.com.wagner.casadocodigo.novoAutor.model.Autor
import br.com.wagner.casadocodigo.novoAutor.repository.AutorRepository
import br.com.wagner.casadocodigo.novoLivro.model.Livro
import br.com.wagner.casadocodigo.novoLivro.repository.LivroRepository
import br.com.wagner.casadocodigo.novoLivro.request.NovoLivroRequest
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
import java.math.BigDecimal
import java.net.URI
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@Transactional
class NovoLivroControllerTest {

    @field:Autowired
    lateinit var autorRepository: AutorRepository

    @field:Autowired
    lateinit var categoriaRepository: CategoriaRepository

    @field:Autowired
    lateinit var livroRepository: LivroRepository

    @field:Autowired
    lateinit var mockmvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    @BeforeEach
    internal fun setUp() {
        livroRepository.deleteAll()
        autorRepository.deleteAll()
        categoriaRepository.deleteAll()
    }

    // 1 cenario de teste/ caminho feliz

    @Test
    @DisplayName("deve retornar 200, cadastrar um novo livro")
    fun deveRetornar200() {

        // cenario

        val autor = Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        )
        autorRepository.save(autor)
        val idAutor: Long? = autor.id

        val categoria = Categoria(nome = "Romance")
        categoriaRepository.save(categoria)
        val idCategoria: Long? = categoria.id

        val uri = URI("/livros")

        val request = NovoLivroRequest(
            titulo = "Dona flor",
            resumo = "livro maravilhoso",
            sumario = "explicativo",
            preco = BigDecimal(50.0),
            numeroPagina = 200,
            isbn = "uytrtg",
            dataPublicacao = LocalDate.now().plusDays(1L),
            idCategoria = idCategoria!!,
            idAutor = idAutor!!
        )

        mockmvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        // ação

        // assertivas

    }

    // 2 cenario de teste

    @Test
    @DisplayName("deve retornar 400, quando id autor nao existir")
    fun deveRetornar400IdAutorInexistente() {

        // cenario

        val autor = Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        )
        autorRepository.save(autor)


        val categoria = Categoria(nome = "Romance")
        categoriaRepository.save(categoria)
        val idCategoria: Long? = categoria.id

        val uri = URI("/livros")

        val request = NovoLivroRequest(
            titulo = "Dona flor",
            resumo = "livro maravilhoso",
            sumario = "explicativo",
            preco = BigDecimal(50.0),
            numeroPagina = 200,
            isbn = "uytrtg",
            dataPublicacao = LocalDate.now().plusDays(1L),
            idCategoria = idCategoria!!,
            idAutor = 50000L
        )

        mockmvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // ação

        // assertivas

    }

    // 3 cenario de teste

    @Test
    @DisplayName("deve retornar 400, quando id categoria nao existir")
    fun deveRetornar400IdCategoriaInexistente() {

        // cenario

        val autor = Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        )
        autorRepository.save(autor)
        val idAutor: Long? = autor.id


        val categoria = Categoria(nome = "Romance")
        categoriaRepository.save(categoria)

        val uri = URI("/livros")

        val request = NovoLivroRequest(
            titulo = "Dona flor",
            resumo = "livro maravilhoso",
            sumario = "explicativo",
            preco = BigDecimal(50.0),
            numeroPagina = 200,
            isbn = "uytrtg",
            dataPublicacao = LocalDate.now().plusDays(1L),
            idCategoria = 50000L,
            idAutor = idAutor!!
        )

        mockmvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // ação

        // assertivas

    }

    // 4 cenario de teste

    @Test
    @DisplayName("deve retornar 400, quando titulo já estiver cadastrado")
    fun deveRetornar400TituloJaExiste() {

        // cenario

        val autor = Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        )
        autorRepository.save(autor)
        val idAutor: Long? = autor.id


        val categoria = Categoria(nome = "Romance")
        categoriaRepository.save(categoria)
        val idCategoria: Long? = categoria.id

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

        val uri = URI("/livros")

        val request = NovoLivroRequest(
            titulo = "O homem de ferro",
            resumo = "livro maravilhoso",
            sumario = "explicativo",
            preco = BigDecimal(50.0),
            numeroPagina = 200,
            isbn = "uytrtg",
            dataPublicacao = LocalDate.now().plusDays(1L),
            idCategoria = idCategoria!!,
            idAutor = idAutor!!
        )

        mockmvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // ação

        // assertivas

    }

    // 5 cenario de teste

    @Test
    @DisplayName("deve retornar 400, quando preco já menor que 20.0")
    fun deveRetornar400PrecoMenor20() {

        // cenario

        val autor = Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        )
        autorRepository.save(autor)
        val idAutor: Long? = autor.id


        val categoria = Categoria(nome = "Romance")
        categoriaRepository.save(categoria)
        val idCategoria: Long? = categoria.id


        val uri = URI("/livros")

        val request = NovoLivroRequest(
            titulo = "O homem de ferro",
            resumo = "livro maravilhoso",
            sumario = "explicativo",
            preco = BigDecimal(19.0),
            numeroPagina = 200,
            isbn = "uytrtg",
            dataPublicacao = LocalDate.now().plusDays(1L),
            idCategoria = idCategoria!!,
            idAutor = idAutor!!
        )

        mockmvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // ação

        // assertivas

    }

    // 6 cenario de teste

    @Test
    @DisplayName("deve retornar 400, quando data publicação nao for no futuro")
    fun deveRetornar400DataNaoEstiverNoFuturo() {

        // cenario

        val autor = Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        )
        autorRepository.save(autor)
        val idAutor: Long? = autor.id


        val categoria = Categoria(nome = "Romance")
        categoriaRepository.save(categoria)
        val idCategoria: Long? = categoria.id


        val uri = URI("/livros")

        val request = NovoLivroRequest(
            titulo = "O homem de ferro",
            resumo = "livro maravilhoso",
            sumario = "explicativo",
            preco = BigDecimal(20.0),
            numeroPagina = 200,
            isbn = "uytrtg",
            dataPublicacao = LocalDate.now(),
            idCategoria = idCategoria!!,
            idAutor = idAutor!!
        )

        mockmvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // ação

        // assertivas

    }

    // 6 cenario de teste

    @Test
    @DisplayName("deve retornar 400, quando numero pagina menor que 100")
    fun deveRetornar400PaginaMenor100() {

        // cenario

        val autor = Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        )
        autorRepository.save(autor)
        val idAutor: Long? = autor.id


        val categoria = Categoria(nome = "Romance")
        categoriaRepository.save(categoria)
        val idCategoria: Long? = categoria.id


        val uri = URI("/livros")

        val request = NovoLivroRequest(
            titulo = "O homem de ferro",
            resumo = "livro maravilhoso",
            sumario = "explicativo",
            preco = BigDecimal(20.0),
            numeroPagina = 99,
            isbn = "uytrtg",
            dataPublicacao = LocalDate.now().plusDays(1L),
            idCategoria = idCategoria!!,
            idAutor = idAutor!!
        )

        mockmvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // ação

        // assertivas

    }

    // 7 cenario de teste

    @Test
    @DisplayName("deve retornar 400, quando isbn já estiver cadastrado")
    fun deveRetornar400IsbnJaExiste() {

        // cenario

        val autor = Autor(
            nome = "Carol",
            email = "carol@gmail.com",
            descricao = "uma autora legal"
        )
        autorRepository.save(autor)
        val idAutor: Long? = autor.id


        val categoria = Categoria(nome = "Romance")
        categoriaRepository.save(categoria)
        val idCategoria: Long? = categoria.id

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

        val uri = URI("/livros")

        val request = NovoLivroRequest(
            titulo = "Sao joao",
            resumo = "livro maravilhoso",
            sumario = "explicativo",
            preco = BigDecimal(50.0),
            numeroPagina = 200,
            isbn = "uytrtg",
            dataPublicacao = LocalDate.now().plusDays(1L),
            idCategoria = idCategoria!!,
            idAutor = idAutor!!
        )

        mockmvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // ação

        // assertivas

    }

    // metodo para desserializar objeto da requisição

    fun toJson(request: NovoLivroRequest): String {
        return  objectMapper.writeValueAsString(request)
    }
}