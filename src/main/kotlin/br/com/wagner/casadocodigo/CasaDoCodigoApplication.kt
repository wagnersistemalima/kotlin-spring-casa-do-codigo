package br.com.wagner.casadocodigo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@SpringBootApplication
class CasaDoCodigoApplication


fun main(args: Array<String>) {
	runApplication<CasaDoCodigoApplication>(*args)
}
