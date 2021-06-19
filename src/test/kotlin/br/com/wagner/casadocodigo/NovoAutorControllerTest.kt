package br.com.wagner.casadocodigo

import br.com.wagner.casadocodigo.novoAutor.repository.AutorRepository
import br.com.wagner.casadocodigo.novoAutor.request.NovoAutorRequest
import com.fasterxml.jackson.databind.ObjectMapper
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
class NovoAutorControllerTest() {

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    @field:Autowired
    lateinit var autorRepository: AutorRepository

    @field:Autowired
    lateinit var mockMvc: MockMvc

    // 1 cenario de testes / caminho feliz

    @Test
    @DisplayName("deve retornar 200, cadastrar um novo autor")
    fun deveRetornar200(): Unit {
        // cenario

        val request = NovoAutorRequest(nome = "Joao", email = "joao@email.com", descricao = "Um autor legal")

        val uri: URI = URI("/autores")

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request))).andExpect(MockMvcResultMatchers.status().`is`(200))
    }

    // 2 cenario de testes

    @Test
    @DisplayName("deve retornar 400, ao tentar cadastrar um novo autor com nome em branco")
    fun deveRetornar400AoTentarCadastrarNomeEmBranco(): Unit {
        // cenario

        val request = NovoAutorRequest(nome = "", email = "joao@email.com", descricao = "Um autor legal")

        val uri: URI = URI("/autores")

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request))).andExpect(MockMvcResultMatchers.status().`is`(400))
    }

    // 3 cenario de testes

    @Test
    @DisplayName("deve retornar 400, ao tentar cadastrar um novo autor com email em branco")
    fun deveRetornar400AoTentarCadastrarEmailEmBranco(): Unit {
        // cenario

        val request = NovoAutorRequest(nome = "Joao", email = "", descricao = "Um autor legal")

        val uri: URI = URI("/autores")

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request))).andExpect(MockMvcResultMatchers.status().`is`(400))
    }

    // 4 cenario de testes

    @Test
    @DisplayName("deve retornar 400, ao tentar cadastrar um novo autor com descrição em branco")
    fun deveRetornar400AoTentarCadastrarDescricaoEmBranco(): Unit {
        // cenario

        val request = NovoAutorRequest(nome = "Joao", email = "joao@email.com", descricao = "")

        val uri: URI = URI("/autores")

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request))).andExpect(MockMvcResultMatchers.status().`is`(400))
    }

    // 4 cenario de testes

    @Test
    @DisplayName("deve retornar 400, ao tentar cadastrar um novo autor com email com formato invalido")
    fun deveRetornar400AoTentarCadastrarEmailFormatoInvalido(): Unit {
        // cenario

        val request = NovoAutorRequest(nome = "Joao", email = "joaoemail.com", descricao = "Um autor legal")

        val uri: URI = URI("/autores")

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request))).andExpect(MockMvcResultMatchers.status().`is`(400))
    }

    // metodo para desserializar a requisição

    fun toJson(request: NovoAutorRequest): String {
        return objectMapper.writeValueAsString(request)
    }
}