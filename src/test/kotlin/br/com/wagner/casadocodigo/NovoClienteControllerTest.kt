package br.com.wagner.casadocodigo

import br.com.wagner.casadocodigo.novoCliente.model.Cliente
import br.com.wagner.casadocodigo.novoCliente.model.Endereco
import br.com.wagner.casadocodigo.novoCliente.repository.ClienteRepository
import br.com.wagner.casadocodigo.novoCliente.request.EnderecoRequest
import br.com.wagner.casadocodigo.novoCliente.request.NovoClienteRequest
import br.com.wagner.casadocodigo.novoEstado.model.Estado
import br.com.wagner.casadocodigo.novoEstado.repository.EstadoRepository
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
class NovoClienteControllerTest {

    @field:Autowired
    lateinit var paisRepository: PaisRepository

    @field:Autowired
    lateinit var estadoRepository: EstadoRepository

    @field:Autowired
    lateinit var clienteRepository: ClienteRepository

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    // rodar depois de cada teste
    @AfterEach
    internal fun tearDown() {
        estadoRepository.deleteAll()
        paisRepository.deleteAll()
        clienteRepository.deleteAll()
    }

    // 1 cenario de teste / caminho feliz

    @Test
    @DisplayName("deve retornar 200, cadastrar cliente")
    fun deveRetornar200CadastrarCliente() {
        // cenario

        val uri = URI("/clientes")

        val pais = Pais("Argentina")
        paisRepository.save(pais)
        val idPais: Long? = pais.id

        val estado = Estado("Paraiba", pais)
        estadoRepository.save(estado)
        val idEstado: Long? = estado.id

        val endereco = EnderecoRequest(
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
            idPais = idPais!!,
            idEstado = idEstado!!,
            endereco = endereco

        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(200))


        // assertivas
    }

    // 2 cenario de teste

    @Test
    @DisplayName("deve retornar 400, ao tentar cadastrar cliente id pais nao existe")
    fun deveRetornar400CadastrarClienteComIdPaisInexistente() {
        // cenario

        val uri = URI("/clientes")

        val pais = Pais("Argentina")
        paisRepository.save(pais)
        val idPais: Long? = pais.id

        val estado = Estado("Paraiba", pais)
        estadoRepository.save(estado)
        val idEstado: Long? = estado.id

        val endereco = EnderecoRequest(
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
            idPais = 5000,
            idEstado = idEstado!!,
            endereco = endereco

        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))


        // assertivas
    }

    // 3 cenario de teste

    @Test
    @DisplayName("deve retornar 400, ao tentar cadastrar cliente id estado que nao existe")
    fun deveRetornar400CadastrarClienteComIdEstadoInexistente() {
        // cenario

        val uri = URI("/clientes")

        val pais = Pais("Argentina")
        paisRepository.save(pais)
        val idPais: Long? = pais.id

        val estado = Estado("Paraiba", pais)
        estadoRepository.save(estado)
        val idEstado: Long? = estado.id

        val endereco = EnderecoRequest(
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
            idPais = idPais!!,
            idEstado = 5000,
            endereco = endereco

        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))


        // assertivas
    }

    // 4 cenario de teste

    @Test
    @DisplayName("deve retornar 400, ao tentar cadastrar cliente com email ja existente")
    fun deveRetornar400CadastrarClienteComEmailJaCadastrado() {
        // cenario

        val uri = URI("/clientes")

        val pais = Pais("Argentina")
        paisRepository.save(pais)
        val idPais: Long? = pais.id

        val estado = Estado("Paraiba", pais)
        estadoRepository.save(estado)
        val idEstado: Long? = estado.id

        val enderecoRequeste = EnderecoRequest(
            logradouro = "rua das flores",
            bairro = "Catole",
            complemento = "perto da padaria",
            cidade = "Campina grande",
            cep = "58410505",
            telefone = "83993809934"
        )

        val endereco = Endereco(
            logradouro = "rua das flores",
            bairro = "Catole",
            complemento = "perto da padaria",
            cidade = "Campina grande",
            cep = "58410505",
            telefone = "83993809934"
        )

        val cliente = Cliente(
            email = "teste@gmail.com",
            nome = "marco",
            sobreNome = "pontes",
            documento = "67467473010",
            pais = pais,
            estado = estado,
            endereco = endereco
        )

        clienteRepository.save(cliente)

        val request = NovoClienteRequest(
            email = "teste@gmail.com",            // email igual
            nome = "teste",
            sobreNome = "silva",
            documento = "04394450438",
            idPais = idPais!!,
            idEstado = idEstado!!,
            endereco = enderecoRequeste

        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))


        // assertivas
    }

    // 4 cenario de teste

    @Test
    @DisplayName("deve retornar 400, ao tentar cadastrar cliente com documento ja existente")
    fun deveRetornar400CadastrarClienteComDocumentoJaCadastrado() {
        // cenario

        val uri = URI("/clientes")

        val pais = Pais("Argentina")
        paisRepository.save(pais)
        val idPais: Long? = pais.id

        val estado = Estado("Paraiba", pais)
        estadoRepository.save(estado)
        val idEstado: Long? = estado.id

        val enderecoRequeste = EnderecoRequest(
            logradouro = "rua das flores",
            bairro = "Catole",
            complemento = "perto da padaria",
            cidade = "Campina grande",
            cep = "58410505",
            telefone = "83993809934"
        )

        val endereco = Endereco(
            logradouro = "rua das flores",
            bairro = "Catole",
            complemento = "perto da padaria",
            cidade = "Campina grande",
            cep = "58410505",
            telefone = "83993809934"
        )

        val cliente = Cliente(
            email = "fulano@gmail.com",
            nome = "marco",
            sobreNome = "pontes",
            documento = "04394450438",
            pais = pais,
            estado = estado,
            endereco = endereco
        )

        clienteRepository.save(cliente)

        val request = NovoClienteRequest(
            email = "teste@gmail.com",
            nome = "teste",
            sobreNome = "silva",
            documento = "04394450438",     // documento igual
            idPais = idPais!!,
            idEstado = idEstado!!,
            endereco = enderecoRequeste

        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))


        // assertivas
    }

    // metodo para desserializar objeto

    fun toJson(request: NovoClienteRequest): String{
        return objectMapper.writeValueAsString(request)
    }
}