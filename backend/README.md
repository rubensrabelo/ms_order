# Backend

## Descrição


---

## Tecnologias
- Java 17  
- Spring Boot  
- Spring Data JPA  
- PostgreSQL  
- OpenFeign  
- Eureka Client
- Docker 

---

## Diagrama de Classes

```mermaid
classDiagram
  direction LR
  class Product {
    - id: Long
    - name: String
    - description: String
    - price: Double 
  }

  class Order {
    - id: Long
    - created: LocalDateTime
    - totalAmount: Double
    - productsId: Set<Long>
  }

  Order "1" -- "*" Product
```

---

## Integração

Eureka → Descoberta de serviços

OpenFeign → Comunicação com Product Service

API Gateway → Entrada única para requisições