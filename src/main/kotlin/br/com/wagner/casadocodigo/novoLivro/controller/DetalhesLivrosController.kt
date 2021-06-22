package br.com.wagner.casadocodigo.novoLivro.controller

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.exceptions.ResourceNotFoundException
import br.com.wagner.casadocodigo.novoLivro.repository.LivroRepository
import br.com.wagner.casadocodigo.novoLivro.response.DetalhesLivroResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/livros")
class DetalhesLivrosController(@field:Autowired val livroRepository: LivroRepository) {

    val logger = LoggerFactory.getLogger(DetalhesLivrosController::class.java)

    // end point / findById

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<Any> {

        logger.info("Iniciando a busca por id do livro")

        // validação

        if(!livroRepository.existsById(id)) {
            logger.error("Recurso não encontrado 404")
            throw ResourceNotFoundException("recurso não encontrado")
        }

        val possivelLivro = livroRepository.findById(id)

        val livro = possivelLivro.get()

        logger.info("busca por id do livro concluido com sucesso")
        return ResponseEntity.ok().body(DetalhesLivroResponse(livro))
    }
}