package br.com.wagner.casadocodigo.novoAutor.controller

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.novoAutor.model.Autor
import br.com.wagner.casadocodigo.novoAutor.repository.AutorRepository
import br.com.wagner.casadocodigo.novoAutor.request.NovoAutorRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/autores")
class NovoAutorController(@field:Autowired val autorRepository: AutorRepository) {

    private val logger = LoggerFactory.getLogger(NovoAutorController::class.java)

    // end point / insert

    @Transactional
    @PostMapping
    fun insert(@RequestBody @Valid request: NovoAutorRequest): ResponseEntity<Any> {
        logger.info("iniciando cadastro de um novo autor")

        // validação

        if (autorRepository.existsByEmail(request.email)) {
            throw ExceptionGenericValidated("Email unico, já cadastrado para este autor")
        }

        val autor = request.toModel()

        autorRepository.save(autor)

        logger.info("Autor cadastrado com sucesso")
        return ResponseEntity.ok().build()
    }
}