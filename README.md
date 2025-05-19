# Loja Virtual

Este é um projeto fullstack de uma loja virtual com funcionalidades completas para clientes e administradores.

## Funcionalidades

### Cliente
- Catálogo com categorias, filtros e busca
- Carrinho de compras
- Checkout
- Histórico de pedidos
- Autenticação (email/senha e OAuth)
- Avaliações de produtos

### Administrador
- Dashboard administrativo
- Gerenciamento de produtos
- Gerenciamento de pedidos
- Gerenciamento de usuários
- Visualização de estatísticas de vendas

## Tecnologias Utilizadas

### Backend
- Java com Spring Boot
- MySQL

### Frontend
- HTML
- CSS
- JavaScript

## Estrutura do Projeto

```
store/
├── backend/           # Projeto Spring Boot
└── frontend/         # Interface web
```

## Como Executar

### Requisitos
- Java 17+
- MySQL 8+
- Maven
- Node.js (para desenvolvimento frontend)

### Backend
1. Configure o banco de dados MySQL
2. Entre na pasta `backend`
3. Execute: `mvn spring-boot:run`

### Frontend
1. Entre na pasta `frontend`
2. Abra o arquivo `index.html` em seu navegador 