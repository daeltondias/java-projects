# java-projects - Sistema de Gerenciamento de Projetos

Este repositório contém um sistema de gerenciamento de projetos desenvolvido em Java.

## Configuração e Execução do Projeto

### Pré-requisitos

Para executar este projeto, você precisará ter instalado:

- Java 11 ou superior
- Maven 3.6 ou superior

### Passo a Passo para Configuração

1. Clone o repositório:

```bash
git clone https://github.com/daeltondias/java-projects.git
```

2. Navegue até o diretório do projeto:

```bash
cd java-projects
```

3. Instale as dependências do projeto:

```bash
mvn clean install
```

4. Configure o banco de dados:
   - Abra o arquivo `src/main/resources/application.properties`
   - Configure as propriedades de conexão com o banco de dados conforme necessário

### Executando o Projeto

Para iniciar a aplicação, execute:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

### Endpoints da API

O sistema possui os seguintes endpoints principais:

#### Projetos

- Listar todos os projetos: `GET /api/projects`
- Criar um novo projeto: `POST /api/projects/new`
- Obter detalhes de um projeto: `GET /api/projects/{id}`
- Atualizar um projeto: `PATCH /api/projects/{id}`
- Excluir um projeto: `DELETE /api/projects/{id}`

#### Usuários

- Listar todos os usuários: `GET /api/users`
- Criar um novo usuário: `POST /api/users/new`
- Obter detalhes de um usuário: `GET /api/users/{id}`
- Atualizar um usuário: `PATCH /api/users/{id}`

## Executando os Testes

### Testes Unitários

Para executar os testes unitários:

```bash
mvn test
```

### Testes de Integração

Para executar os testes de integração:

```bash
mvn verify
```

### Relatório de Cobertura de Testes

Para gerar um relatório de cobertura de testes:

```bash
mvn jacoco:report
```

O relatório estará disponível em `target/site/jacoco/index.html`

## Estrutura do Projeto

O projeto segue uma arquitetura em camadas:

- **Controller**: Responsável por receber as requisições HTTP
- **Service**: Contém a lógica de negócio
- **DTO**: Objetos de transferência de dados
- **Model**: Entidades do banco de dados
