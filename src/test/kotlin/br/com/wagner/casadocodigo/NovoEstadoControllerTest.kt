package br.com.wagner.casadocodigo

import br.com.wagner.casadocodigo.novoEstado.model.Estado
import br.com.wagner.casadocodigo.novoEstado.repository.EstadoRepository
import br.com.wagner.casadocodigo.novoEstado.request.NovoEstadoRequest
import br.com.wagner.casadocodigo.novoPais.model.Pais
import br.com.wagner.casadocodigo.novoPais.repository.PaisRepository
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
class NovoEstadoControllerTest {

    @field:Autowired
    lateinit var paisRepository: PaisRepository

    @field:Autowired
    lateinit var estadoRepository: EstadoRepository

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    // rodar depois de cada teste
    @AfterEach
    internal fun tearDown() {
        estadoRepository.deleteAll()
        paisRepository.deleteAll()
    }

    // 1 cenario de teste/ caminho feliz

    @Test
    @DisplayName("deve retornar 200, cadastrar um estado")
    fun deveRetornar200CadastrarEstado() {

        // cenario

        val uri = URI("/estados")

        val pais = Pais("Argentina")
        paisRepository.save(pais)

        val idPais: Long? = pais.id

        val request = NovoEstadoRequest(nome = "Paraiba", idPais = idPais!!)

        //ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        //assertivas
    }

    // 2 cenario de teste

    @Test
    @DisplayName("deve retornar 400, ao tentar cadastrar um nome de estado em vazio")
    fun deveRetornar400TentarCadastrarNomeEstadoVazio() {

        // cenario

        val uri = URI("/estados")

        val pais = Pais("Argentina")
        paisRepository.save(pais)

        val idPais: Long? = pais.id

        val request = NovoEstadoRequest(nome = "", idPais = idPais!!)

        //ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        //assertivas
    }

    // 3 cenario de teste

    @Test
    @DisplayName("deve retornar 400, ao tentar cadastrar um estado a um pais inexistente")
    fun deveRetornar400TentarCadastrarEstadoPaisInexistente() {

        // cenario

        val uri = URI("/estados")

        val pais = Pais("Argentina")
        paisRepository.save(pais)

        val idPais: Long? = pais.id

        val request = NovoEstadoRequest(nome = "Paraiba", idPais = 5000L)

        //ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        //assertivas
    }

    // 4 cenario de teste

    @Test
    @DisplayName("deve retornar 400, ao tentar cadastrar um estado que ja existe a o pais")
    fun deveRetornar400TentarCadastrarEstadoQueJaExisteAOPais() {

        // cenario

        val uri = URI("/estados")

        val pais = Pais("Argentina")
        paisRepository.save(pais)
        val idPais: Long? = pais.id

        val estado = Estado("Paraiba", pais)
        estadoRepository.save(estado)

        val request = NovoEstadoRequest(nome = "Paraiba", idPais = idPais!!)

        //ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        //assertivas
    }


    // metodo para desserializar objeto

    fun toJson(request: NovoEstadoRequest): String {
        return objectMapper.writeValueAsString(request)
    }

}