# API Gateway

## Descrição

O **API Gateway** atua como ponto único de entrada para todas as requisições direcionadas aos microserviços do sistema.
Ele é responsável por **rotear, filtrar e gerenciar** o tráfego, garantindo segurança, controle e simplificação na comunicação entre o cliente e os serviços internos.

---

## Tecnologias Utilizadas

* **Spring Boot**
* **Eureka Client**
* **Spring Cloud Gateway** *(roteamento e filtragem de requisições)*
* **Docker**
* **Docker Compose**

---

## Como Testar com Docker Compose

1. **Clonar o repositório e acessar a pasta do backend**:

   ```bash
   git clone <url-do-repositorio>
   cd backend
   ```

2. **Subir os serviços**:

   ```bash
   docker-compose -p nome-do-projeto up -d --build
   ```

3. **Acessar via API Gateway**:

   * URL base: [http://localhost:8080](http://localhost:8080)
   * As rotas configuradas no Gateway redirecionarão para os microserviços correspondentes.

---