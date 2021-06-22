package br.com.wagner.casadocodigo.novoLivro.controller

import br.com.wagner.casadocodigo.novoLivro.repository.LivroRepository
import br.com.wagner.casadocodigo.novoLivro.response.ListarLivrosResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collector
import java.util.stream.Collectors

@RestController
@RequestMapping("/livros")
class ListarLivrosController(@field:Autowired val livroRepository: LivroRepository) {

    val logger = LoggerFactory.getLogger(ListarLivrosController::class.java)


    // end point findAll

    @Transactional
    @GetMapping
    fun findAll(): ResponseEntity<Any> {
        logger.info("Iniciando a listagem dos livros")
        val lista = livroRepository.findAll()

        logger.info("Listagem retornada com sucesso")
        return  ResponseEntity.ok().body(lista.stream().map { livro -> ListarLivrosResponse(livro) }.collect(Collectors.toList()))
    }
}