package br.com.wagner.casadocodigo

import br.com.wagner.casadocodigo.novaCategoria.model.Categoria
import br.com.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository
import br.com.wagner.casadocodigo.novaCategoria.request.NovaCategoriaRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
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
import java.net.URI

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@Transactional
class NovaCategoriaControllerTest {

    @field:Autowired
    lateinit var categoriaRepository: CategoriaRepository

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    @AfterEach
    internal fun tearDown() {
        categoriaRepository.deleteAll()
    }

    // 1 cenario de teste / caminho feliz

    @Test
    @DisplayName("deve retornar 200, cadastrar uma nova categoria")
    fun deveRetornar200() {
        // cenario

        val uri = URI("/categorias")

        val request = NovaCategoriaRequest(nome = "Fantasia")

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        // ação


        // assertivas
    }

    // 2 cenario de teste

    @Test
    @DisplayName("deve retornar 400, quando nome em branco")
    fun deveRetornar400QuandoNomeEmBranco() {
        // cenario

        val uri = URI("/categorias")

        val request = NovaCategoriaRequest(nome = "")

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // ação


        // assertivas
    }

    // 3 cenario de teste

    @Test
    @DisplayName("deve retornar 400, quando nome da categoria ja estiver cadastrado")
    fun deveRetornar400QuandoNomeCategoriaJacadastrado() {
        // cenario

        val categoria = Categoria("Terror")
        categoriaRepository.save(categoria)

        val uri = URI("/categorias")

        val request = NovaCategoriaRequest(nome = "Terror")

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // ação


        // assertivas
    }


    // metodo para desserializar objeto da requisição

    fun toJson(request: NovaCategoriaRequest): String {
        return objectMapper.writeValueAsString(request)
    }
}