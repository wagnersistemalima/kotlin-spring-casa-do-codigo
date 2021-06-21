# treino-duru-jogo-facil-casa-do-codigo
desenvolver uma api para suportar parte do funcionamento da casa do código. Temos várias funcionalidades de dificuldade progressiva e com uma regra de negócio que eu imagino que seja de um entendimento mais direto.

## Setup do Projeto - Casa do código
* Linguagem de programação: Kotlin
* Tecnologia: Spring Boot
* Gerenciador de dependência: Gradlle

## Sobre as restrições
### Limitação de uso de bibliotecas externas
* Neste projeto não está liberado usar bibliotecas que geram código ou que fazem conversão de objetos de entrada para objetos de domínios. Nesse conjunto, temos alguns exemplos:
* Lombok
* Model mapper
* Map Struct

## Limitação na criação de setters em classes de domínio
* Você só pode ter no máximo dois métodos no estilo setter nas suas classes anotadas com @Entity ou com qualquer outra anotação da JPA.
* Caso você crie mais de dois setters é importante que você entenda que seu códido não está correto do ponto de vista do nosso treinamento.

## Limitação na criação de services
* Você não precisa de nenhum service para implementar esse código.
