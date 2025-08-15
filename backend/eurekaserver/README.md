# Eureka Server

## Descrição

O **Eureka Server** é responsável pela **descoberta de microserviços** neste projeto.
Ele permite que cada serviço se registre e descubra automaticamente outros serviços disponíveis, facilitando a comunicação e garantindo a escalabilidade da arquitetura.

---

## Tecnologias Utilizadas

* **Spring Boot**
* **Spring Security** *(proteção do painel Eureka)*
* **Eureka Server** *(registro e descoberta de serviços)*
* **Docker**
* **Docker Compose**

---

## Como Testar com Docker Compose

1. **Clonar o repositório** e acessar a pasta do backend:

   ```bash
   git clone <url-do-repositorio>
   cd backend
   ```

2. **Subir os serviços**:

   ```bash
   docker-compose -p nome-do-projeto up -d --build
   ```

3. **Acessar o painel Eureka**:

   * URL: [http://localhost:8761](http://localhost:8761)
   * Efetuar login *(se configurado no Spring Security)*.
