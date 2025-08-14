# Microservices Project - Product & Order

## Descrição
Este projeto é um exemplo simples de arquitetura de **microserviços** usando **Spring Boot**, com dois serviços principais:
- **Product Service** → Gerencia produtos.
- **Order Service** → Gerencia pedidos e se comunica com o Product Service via **OpenFeign**.

---

## Estrutura

### backend/
Contém todo o código dos microserviços (Product Service, Order Service, Eureka Server e API Gateway).  
Os serviços estão configurados para rodar via **Docker Compose**.

---
