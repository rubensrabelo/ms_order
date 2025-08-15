# Projeto de Microsserviços - Pedidos

## Descrição

Este projeto é um exemplo prático desenvolvido para estudo da arquitetura de **microserviços** com **Spring Boot**.
Atualmente, conta com dois serviços principais, integrados via **OpenFeign**:

* **Product Service** → Gerencia o cadastro e manutenção de produtos.
* **Order Service** → Gerencia os pedidos e se comunica com o Product Service para obtenção de dados.

Em etapas futuras, será desenvolvido um **frontend em React** para interação com os serviços.
Além disso, o projeto será expandido para incorporar e testar outros conceitos da arquitetura de microserviços.

---

## Estrutura do Projeto

### **frontend/**

* Contém o código do aplicativo React *(em desenvolvimento futuro)*.

### **backend/**

* Reúne todos os microserviços e componentes de infraestrutura:

  * **Product Service**
  * **Order Service**
  * **Eureka Server** (descoberta de serviços)
  * **API Gateway** (roteamento e balanceamento de requisições)

---
