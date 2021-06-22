package br.com.wagner.casadocodigo.novoCliente.controller

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.novoCliente.repository.ClienteRepository
import br.com.wagner.casadocodigo.novoCliente.request.NovoClienteRequest
import br.com.wagner.casadocodigo.novoEstado.repository.EstadoRepository
import br.com.wagner.casadocodigo.novoPais.repository.PaisRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/clientes")
class NovoClienteController(
    @field:Autowired val clienteRepository: ClienteRepository,
    @field:Autowired val paisRepository: PaisRepository,
    @field:Autowired val estadoRepository: EstadoRepository
) {

    val logger = LoggerFactory.getLogger(NovoClienteController::class.java)

    // end point insert

    @Transactional
    @PostMapping
    fun insert(@Valid @RequestBody request: NovoClienteRequest): ResponseEntity<Any> {
        logger.info("Iniciando cadastro de um novo cliente")

        // validação email unico e documento

        if(clienteRepository.existsByEmail(request.email)) {
            logger.error("Entrou no if, email ja cadastrado")
            throw ExceptionGenericValidated("Campo unico, email ja cadastrado")
        }

        // validação documento unico

        if(clienteRepository.existsByDocumento(request.documento)) {
            logger.error("entrou no if, documento ja cadastrado")
            throw  ExceptionGenericValidated("Campo unico, documento ja cadastrado")
        }

        val cliente = request.toModel(paisRepository, estadoRepository)
        clienteRepository.save(cliente)

        logger.info("Cadastro de um novo cliente concluido com sucesso!")
        return ResponseEntity.ok().build()

    }
}