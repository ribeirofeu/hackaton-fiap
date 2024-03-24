# Aplicação Spring - README

Este repositório contém uma aplicação que representa o MVP (Minimum Viable Product) de um sistema de registro de ponto. Desenvolvida para atender aos requisitos funcionais do Hackathon FIAP - Software Architecture 2024, esta aplicação busca fornecer uma solução inicial que aborde os desafios propostos no evento.

## Pré-requisitos

Antes de executar a aplicação, certifique-se de ter o seguinte instalado:

- Java Development Kit (JDK) versão 17 ou superior
- Maven

## Configuração

1. Configure as credenciais do banco de dados MySQL no arquivo `application.properties`.

2. Compile a aplicação:

```bash
mvn clean package 
```

## Execução

Execute a aplicação Spring com o seguinte comando:

```bash
java -jar target/hackton-0.0.1-SNAPSHOT.jar
```
A aplicação será iniciada e estará pronta para receber requisições.

## Endpoints

### 1. Port: 8080

### 2. Swagger: /swagger-ui/index.htm

### 3. GET /clock-register

- **Descrição:** Gera o relatório de registro de pontos do colaborador.
- **Parâmetros:**
  - `startDate`: Data de início do período desejado.
  - `finalDate`: Data de término do período desejado.
- **Autenticação:** Requer autenticação por JWT.

### 4. POST /clock-register

- **Descrição:** Registra o ponto do colaborador.
- **Autenticação:** Requer autenticação por JWT.
- **Corpo da Requisição:** Não requer parâmetros.

### 5. GET /clock-report

- **Descrição:** Requisita o relatório que é enviado por e-mail na extensão .xlsx.
- **Parâmetros:**
  - `month`: Mês para o qual o relatório deve ser gerado.
  - `year`: Ano para o qual o relatório deve ser gerado.
- **Autenticação:** Requer autenticação por JWT.

### 6. POST /auth/login

- **Descrição:** Autentica o usuário e obtém um JWT.
- **Corpo da Requisição:**
  - `login` (e-mail)
  - `password`
- **Autenticação:** Não requer autenticação por JWT.

## Autenticação JWT

A aplicação utiliza autenticação JWT para proteger os endpoints. Após autenticar-se com sucesso no endpoint `/auth/login`, o token JWT é gerado e deve ser incluído nos cabeçalhos das requisições subsequentes para acessar os recursos protegidos.

## Considerações Finais

Esta aplicação Spring oferece uma maneira simples e segura de gerenciar o registro de pontos de colaboradores e gerar relatórios. Certifique-se de seguir as instruções de configuração e execução para utilizar a aplicação corretamente.
