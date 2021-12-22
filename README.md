# PoupeAí

Manter um controle das despesas e guardar dinheiro pode ser uma tarefa difícil, principalmente quando não se há tempo para estruturar um planejamento financeiro. Outros fatores também podem contribuir para uma vida financeira não saudável, como a falta de disciplina e até mesmo a falta de familiaridade com ferramentas digitais, que poderiam tornar todo o processo mais prático.

Pensando nisso, o PoupeAí tem a missão de apresentar uma solução digital, baseada no [Método dos 10 Envelopes](https://economia.uol.com.br/financas-pessoais/noticias/redacao/2016/08/22/metodo-dos-10-envelopes-ajuda-a-cortar-gastos-e-a-guardar-dinheiro-todo-mes.htm), que seja simples e intuitiva.

# Tecnologias utilizadas

- Java 8
- Spring Framework
- PostgreSQL  
- Lombok: reduzir código boilerplate do java
- MapStruct: converter os DTOs(Data transfer object) de entrada e saída
- SpringDoc: documentar a API com o swagger

# Como executar este projeto

Para executar localmente é necessário ter instalado na sua máquina: Java, PostgreSQL. Em seguida, clone o projeto através de:
```
git@github.com:karinamaria/poupeAi.git
```
Abra o projeto em uma IDE de sua preferência. Logo, depois execute o projeto através do maven:
```
mvn spring-boot:run
```
Ou execute a classe:
```
PoupeAiApplication.java
```
Abra seu navegador de preferência e coloque em: `http://localhost:8080/swagger-ui.html`

# Observações

Para conseguir executar o projeto é necessário configurar na sua IDE as variáveis de ambiente descritas no arquivo `application.yml`. 

Essas variáveis descrevem detalhes da conexão da aplicação com o banco de dados.

# Desenvolvedoras

* Karina Maria [@karinamaria](https://github.com/karinamaria/)
* Maria Eduarda [@mariaeloi](https://github.com/mariaeloi/)