package br.com.wagner.casadocodigo.novoPais.controller

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.novoPais.repository.PaisRepository
import br.com.wagner.casadocodigo.novoPais.request.NovoPaisRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/pais")
class NovoPaisController(@field:Autowired val paisRepository: PaisRepository) {

    val logger = LoggerFactory.getLogger(NovoPaisController::class.java)

    // end point / insert

    @PostMapping
    fun insert(@Valid @RequestBody request: NovoPaisRequest): ResponseEntity<Any> {

        logger.info("Iniciando cadastro de um novo pais")

        // validação campo unico

        if(paisRepository.existsByNome(request.nome)) {
            logger.error("entrou no if, nome do pais ja cadastrado")
            throw ExceptionGenericValidated("Campo unico, nome do pais já cadastrado")
        }

        val pais = request.toModel()
        paisRepository.save(pais)

        logger.info("cadastro de um pais concluido com sucesso")
        return ResponseEntity.ok().build()

    }
}