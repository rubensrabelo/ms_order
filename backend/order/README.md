# Order Service

## Descrição

O **Order Service** é responsável pelo gerenciamento de pedidos do sistema.
Ele permite criar, listar, buscar, atualizar e deletar pedidos, além de se comunicar com o **Product Service** via **OpenFeign** para obter informações completas dos produtos.
O serviço se registra no **Eureka Server** e pode ser acessado via **API Gateway**.

---

## Tecnologias Utilizadas

* **Spring Boot**
* **Spring Data JPA**
* **ModelMapper** *(mapeamento de DTOs)*
* **PostgreSQL**
* **Flyway** *(migração de banco de dados)*
* **Eureka Client** *(descoberta de serviços)*
* **OpenFeign** *(comunicação entre microserviços)*
* **Spring Cloud Gateway** *(roteamento de requisições)*
* **Docker**
* **Docker Compose**

---

## Estrutura de Pastas

```
order/
 ├── .gitignore
 ├── Dockerfile
 ├── docker-compose.yml
 ├── README.md
 ├── pom.xml
 └── src/
      └── main/
          ├── java/
          │    └── io/github/rubensrabelo/order/
          │         ├── application/
          │         │    ├── controller/
          │         │    │     └── OrderController.java
          │         │    ├── service/
          │         │    │     └── OrderService.java
          │         │    ├── dto/
          │         │    │     ├── order/
          │         │    │     │     ├── OrderCreateDTO.java
          │         │    │     │     ├── OrderResponseDTO.java
          │         │    │     ├── product/
          │         │    │           └── ProductResponseDTO.java
          │         │    ├── handler/
          │         │    │     ├── exceptions/
          │         │    │     │     ├── DatabaseException.java
          │         │    │     │     └── ResourceNotFoundException.java
          │         │    │     ├── ResourceExceptionHandler.java
          │         │    │     └── StandardError.java
          │         ├── config/
          │         │     └── ModelMapperConfig.java
          │         ├── domain/
          │         │     └── Order.java
          │         ├── infra/
          │         │     ├── repository/
          │         │     │     └── OrderRepository.java
          │         │     └── clients/
          │         │           └── ProductResourceClient.java
          │         └── OrderApplication.java
          └── resources/
                ├── application.yml
                └── db/migration/
                      ├── V1__Create_Table_Order.sql
                      └── V2__Create_Table_Order_Products.sql
```

---

### 🔹 Observações importantes

* **Migrations com Flyway**:

  * `V1__Create_Table_Order.sql` → Cria a tabela de pedidos.
  * `V2__Create_Table_Order_Products.sql` → Cria a tabela de relacionamento entre pedidos e produtos.

* **OpenFeign**: usado para comunicação com o **Product Service** e trazer detalhes completos dos produtos no pedido.

* **Docker / Docker Compose**: permitem subir o serviço isoladamente ou junto com outros microserviços.

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

   * URL base: [http://localhost:8080/orders](http://localhost:8080/orders)

---

## Exemplos de Requisições *(Insomnia)*

### **1️⃣ Listar todos os pedidos**

**GET** `http://localhost:8080/orders`

**Resposta**

```json
[
  {
    "id": 1,
    "created": "2025-08-15T09:30:00",
    "totalAmount": 5620.00,
    "products": [
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
  }
]
```

---

### **2️⃣ Obter um pedido por ID**

**GET** `http://localhost:8080/orders/1`

**Resposta**

```json
{
  "id": 1,
  "created": "2025-08-15T09:30:00",
  "totalAmount": 5620.00,
  "products": [
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
}
```

---

### **3️⃣ Criar um novo pedido**

**POST** `http://localhost:8080/orders`

**Body (JSON)**

```json
{
  "productsId": [1, 2]
}
```

**Resposta**

```json
{
  "id": 2,
  "created": "2025-08-15T10:00:00",
  "totalAmount": 5620.00,
  "products": [
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
}
```

---