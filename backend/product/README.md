# Product Service

## Descrição

O **Product Service** é responsável pelo gerenciamento de produtos do sistema.
Ele expõe endpoints para criar, listar, buscar, atualizar e excluir produtos, integrando-se ao **Eureka Server** e sendo acessado via **API Gateway**.

---

## Tecnologias Utilizadas

* **Spring Boot**
* **Spring Data JPA**
* **ModelMapper** *(mapeamento de DTOs)*
* **PostgreSQL**
* **Flyway** *(migração de banco de dados)*
* **Eureka Client** *(descoberta de serviços)*
* **Spring Cloud Gateway** *(roteamento e filtragem de requisições)*
* **Docker**
* **Docker Compose**

---

## Estrutura de Pastas

```
product/
 ├── .gitignore
 ├── Dockerfile
 ├── docker-compose.yml
 ├── README.md
 ├── pom.xml
 └── src/
      └── main/
          ├── java/
          │    └── io/github/rubensrabelo/product/
          │         ├── application/
          │         │    ├── controller/
          │         │    │     └── ProductController.java
          │         │    ├── dto/
          │         │    │     ├── ProductCreateDTO.java
          │         │    │     ├── ProductResponseDTO.java
          │         │    │     └── ProductUpdateDTO.java
          │         │    ├── handler/
          │         │    │     ├── exceptions/
          │         │    │     │     ├── DatabaseException.java
          │         │    │     │     └── ResourceNotFoundException.java
          │         │    │     ├── ResourceExceptionHandler.java
          │         │    │     └── StandardError.java
          │         │    └── service/
          │         │          └── ProductService.java
          │         ├── config/
          │         │     └── ModelMapperConfig.java
          │         ├── domain/
          │         │     └── Product.java
          │         ├── infra/
          │         │     └── repository/
          │         │           └── ProductRepository.java
          │         └── ProductApplication.java
          └── resources/
                ├── application.yml
                └── db/migration/
                      ├── V1__Create_Table_Product.sql
                      └── V2__Insert_Sample_Products.sql
```

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

   * URL base: [http://localhost:8080/products](http://localhost:8080/products)

---

## Exemplos de Requisições *(Insomnia)*

### **1️⃣ Listar todos os produtos**

**GET** `http://localhost:8080/products`

**Resposta**

```json
[
  {
    "id": 1,
    "name": "Notebook Dell",
    "description": "Notebook i7 com 16GB RAM",
    "price": 4500.00
  },
  {
    "id": 2,
    "name": "Mouse Logitech",
    "description": "Mouse sem fio",
    "price": 120.00
  }
]
```

---

### **2️⃣ Obter um produto por ID**

**GET** `http://localhost:8080/products/1`

**Resposta**

```json
{
  "id": 1,
  "name": "Notebook Dell",
  "description": "Notebook i7 com 16GB RAM",
  "price": 4500.00
}
```

---

### **3️⃣ Criar um novo produto**

**POST** `http://localhost:8080/products`

**Body (JSON)**

```json
{
  "name": "Teclado Mecânico",
  "description": "Teclado mecânico RGB",
  "price": 350.00
}
```

**Resposta**

```json
{
  "id": 3,
  "name": "Teclado Mecânico",
  "description": "Teclado mecânico RGB",
  "price": 350.00
}
```

---

### **4️⃣ Atualizar um produto**

**PUT** `http://localhost:8080/products/3`

**Body (JSON)**

```json
{
  "name": "Teclado Mecânico RGB",
  "description": "Teclado mecânico RGB com switches vermelhos",
  "price": 370.00
}
```

**Resposta**

```json
{
  "id": 3,
  "name": "Teclado Mecânico RGB",
  "description": "Teclado mecânico RGB com switches vermelhos",
  "price": 370.00
}
```

---

### **5️⃣ Deletar um produto**

**DELETE** `http://localhost:8080/products/3`

**Resposta**

```
204 No Content
```
