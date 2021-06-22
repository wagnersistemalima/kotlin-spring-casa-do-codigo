package br.com.wagner.casadocodigo

import br.com.wagner.casadocodigo.novoPais.model.Pais
import br.com.wagner.casadocodigo.novoPais.repository.PaisRepository
import br.com.wagner.casadocodigo.novoPais.request.NovoPaisRequest
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
class NovoPaisControllerTest {

    @field:Autowired
    lateinit var paisRepository: PaisRepository

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    // rodar depois de cada teste

    @AfterEach
    internal fun tearDown() {
        paisRepository.deleteAll()
    }

    // 1 cenario de testes/ caminho feliz

    @Test
    @DisplayName("deve retornar 200, cadastrar um novo pais")
    fun deveRetornar200Cadastrar() {
        // cenario

        val uri = URI("/pais")

        val request = NovoPaisRequest("Brasil")

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        // assertivas
    }

    // 2 cenario de testes

    @Test
    @DisplayName("deve retornar 400, quando nome pais vinher vazio")
    fun deveRetornar400NomePaisVazio() {
        // cenario

        val uri = URI("/pais")

        val request = NovoPaisRequest("")

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // 3 cenario de testes

    @Test
    @DisplayName("deve retornar 400, quando nome pais ja estiver cadastrado")
    fun deveRetornar400NomePaisJaEstivercadastrado() {
        // cenario

        val pais = Pais("Portugal")
        paisRepository.save(pais)

        val uri = URI("/pais")

        val request = NovoPaisRequest("Portugal")

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }


    // metodo para desserializar objeto da requisição

    fun toJson(request: NovoPaisRequest): String {
        return objectMapper.writeValueAsString(request)
    }

}