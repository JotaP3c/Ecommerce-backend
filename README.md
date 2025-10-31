 Ecommerce Backend
 
API RESTful desenvolvida em **Java 17** com **Spring Boot 3.5.6**, conectada ao banco **PostgreSQL**.  
O projeto foi estruturado para rodar localmente ou em container Docker.
 
---
 
 Tecnologias
 
- Java 17  
- Spring Boot 3.5.6  
- Spring Data JPA  
- Spring Security (JWT)  
- PostgreSQL  
- Maven  
- Docker / Docker Compose  
 
---
 Estrutura do Projeto
 
src/
├─ main/
│   ├─ java/com/catalogoLojaProdutos/
│   │   ├─ controller/   → Controladores REST
│   │   ├─ model/        → Entidades JPA
│ │ ├─ repository/ → Interfaces do JPA Repository
│ │ ├─ service/ → Regras de negócio
│ │ └─ security/ → Configuração JWT e autenticação
│ └─ resources/
│ ├─ application.properties → Configuração de banco e servidor
