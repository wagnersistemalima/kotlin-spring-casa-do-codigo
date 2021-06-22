package br.com.wagner.casadocodigo.novoEstado.controller

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.novoEstado.repository.EstadoRepository
import br.com.wagner.casadocodigo.novoEstado.request.NovoEstadoRequest
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
@RequestMapping("estados")
class NovoEstadoController(

    @field:Autowired val estadoRepository: EstadoRepository,

    @field:Autowired val paisRepository: PaisRepository

    ) {

    val logger = LoggerFactory.getLogger(NovoEstadoController::class.java)

    // end point insert

    @Transactional
    @PostMapping
    fun insert(@Valid @RequestBody request: NovoEstadoRequest): ResponseEntity<Any> {
        logger.info("iniciando cadastro de um novo estado")

        // validação

        if(estadoRepository.existsByNomeAndPais_Id(request.nome, request.idPais)) {
            logger.error("Entrou no if, nome do estado ja cadastrado para este id pais")
            throw ExceptionGenericValidated("Campo unico, nome do estado ja cadastrado para id deste pais")
        }

        val estado = request.toModel(paisRepository)
        estadoRepository.save(estado)

        logger.info("Estado cadastrado com sucesso")
        return  ResponseEntity.ok().build()
    }

}