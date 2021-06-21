package br.com.wagner.casadocodigo.novaCategoria.controller

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.novaCategoria.model.Categoria
import br.com.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository
import br.com.wagner.casadocodigo.novaCategoria.request.NovaCategoriaRequest
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
@RequestMapping("/categorias")
class NovaCategoriaController(@field:Autowired val categoriaRepository: CategoriaRepository) {

    val logger = LoggerFactory.getLogger(NovaCategoriaController::class.java)

    // end point / insert categoria

    @Transactional
    @PostMapping
    fun insert(@Valid @RequestBody request: NovaCategoriaRequest): ResponseEntity<Any> {
        logger.info("Iniciando cadstro de uma categoria....")

        // validação

        if(categoriaRepository.existsByNome(request.nome)) {
            logger.error("Entrou no if, nome ja cadastrado para esta categoria")
            throw ExceptionGenericValidated("Campo unico, nome ja cadastrado para esta categoria")
        }

        val categoria = request.toModel()
        categoriaRepository.save(categoria)

        logger.info("categoria cadastrada com sucesso")
        return ResponseEntity.ok().build()
    }
}