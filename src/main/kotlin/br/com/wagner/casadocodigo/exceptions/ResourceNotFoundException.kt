package br.com.wagner.casadocodigo.exceptions

import java.lang.RuntimeException

class ResourceNotFoundException(val msg: String): RuntimeException(msg) {
}