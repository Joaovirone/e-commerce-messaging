# E-commerce System: Pedido Service (Microsserviço)

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.x-ff69b4.svg)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED.svg)

Este é o microsserviço base responsável pelo gerenciamento de pedidos de uma arquitetura de E-commerce orientada a eventos (EDA - Event-Driven Architecture). Ele foi construído com foco em alta disponibilidade, testes automatizados (TDD) e práticas de infraestrutura como código (DevOps).

## Arquitetura e Fluxo

O serviço atua como o ponto de entrada para novas compras. O fluxo de funcionamento consiste em:
1. Receber o payload do pedido via **API REST**.
2. Persistir o pedido no **PostgreSQL** com o status inicial `PENDENTE`.
3. Publicar um evento (`pedido.criado`) de forma assíncrona no **RabbitMQ**.
4. Retornar o `Status 201 Created` para o cliente sem bloquear a thread esperando validações de estoque.

Esse modelo garante que o serviço de pedidos continue operando rapidamente mesmo que outros serviços (como o Inventário) estejam temporariamente indisponíveis.

## Tecnologias e Práticas Adotadas

- **Java 21**: Utilização de `records` para DTOs imutáveis, garantindo thread-safety e melhor performance.
- **Spring Boot**: REST APIs, Spring Data JPA, Spring AMQP.
- **PostgreSQL**: Banco de dados relacional isolado para este domínio.
- **RabbitMQ**: Message Broker gerenciando exchanges e filas.
- **TDD (Test-Driven Development)**: Cobertura de regras de negócio com JUnit 5 e Mockito.
- **Docker & Docker Compose**: Orquestração local.
- **Multi-stage Docker Build**: O `Dockerfile` da aplicação foi otimizado utilizando múltiplos estágios (Builder + JRE Runtime), garantindo uma imagem final segura (rodando com usuário não-root) e extremamente leve.

## Como Executar o Projeto

Todo o ambiente (Banco de dados, Mensageria e a própria Aplicação) está empacotado e orquestrado no Docker Compose. 

### Pré-requisitos
- [Docker](https://docs.docker.com/get-docker/) e [Docker Compose](https://docs.docker.com/compose/install/) instalados.

### Passos

1. Clone o repositório:
   ```bash
   git clone [https://github.com/Joaovirone/e-commerce-messaging.git](https://github.com/Joaovirone/e-commerce-messaging.git)
   cd nome-do-repo

2. Suba o conteiner:
   ```
   docker compose upp -build

3. Verifique se os serviços estão rodando:
   ```
   API (Aplicação): http://localhost:8080
   
   RabbitMQ Management UI: http://localhost:15002 (Usuário: admin / Senha: admin123)
