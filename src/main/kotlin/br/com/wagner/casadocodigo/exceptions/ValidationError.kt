package br.com.wagner.casadocodigo.exceptions

import java.time.Instant

data class ValidationError(

    val timestamp: Instant,
    val status: Int,
    val error: String,
    val message: String,
    val path: String

){

    val errors: ArrayList<FieldMessage> = ArrayList<FieldMessage>()

    // metodo para adicionar erro na lista de erros

    fun addError(fiedName: String, message: String) {

        this.errors.add(FieldMessage(fiedName, message))

    }
}
