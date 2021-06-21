package br.com.wagner.casadocodigo.novoLivro.controller

import br.com.wagner.casadocodigo.exceptions.ExceptionGenericValidated
import br.com.wagner.casadocodigo.novaCategoria.repository.CategoriaRepository
import br.com.wagner.casadocodigo.novoAutor.repository.AutorRepository
import br.com.wagner.casadocodigo.novoLivro.repository.LivroRepository
import br.com.wagner.casadocodigo.novoLivro.request.NovoLivroRequest
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
@RequestMapping("livros")
class NovoLivroController(

    @field:Autowired val livroRepository: LivroRepository,
    @field:Autowired val autorRepository: AutorRepository,
    @field:Autowired val categoriaRepository: CategoriaRepository
    ) {

    val logger = LoggerFactory.getLogger(NovoLivroController::class.java)
    // end point / inser livro

    @PostMapping
    fun insert(@Valid @RequestBody request: NovoLivroRequest): ResponseEntity<Any> {
        logger.info("Iniciando cadstro de um novo livro...")

        // validacao

        if(livroRepository.existsByTitulo(request.titulo)) {
            throw ExceptionGenericValidated("Campo unico, titulo já cadastrado para este livro")
        }

        val livro = request.toModel(autorRepository, categoriaRepository)
        livroRepository.save(livro)

        return  ResponseEntity.ok().build()
    }
}