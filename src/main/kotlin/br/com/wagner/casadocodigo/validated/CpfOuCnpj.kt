package br.com.wagner.casadocodigo.validated



import org.hibernate.validator.constraints.CompositionType
import org.hibernate.validator.constraints.ConstraintComposition
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.Constraint
import kotlin.reflect.KClass


@MustBeDocumented     // esta validação vai aparecer na documentação da classe que utiliza-la
@Constraint(validatedBy = [])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR)
annotation class CpfOuCnpj(
    val message: String = "CPF ou CNPJ Inválido",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Any>> = []
)


