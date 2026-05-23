# PetPath AI — API Java Advanced

> FIAP Challenge 2026 · Parceiro: CLYVO VET · Disciplina: Java Advanced · Sprint 1

## Integrantes

| Nome | RM |
|---|---|
| Eduardo Augusto de Oliveira Souza | RM565269 |
| Fellipe Costa de Oliveira | RM564673  |
| Felype Ferreira Maschio | RM563009  |
| Gustavo Vieira de Matos | RM563304 |
| Pedro Henrique dos Santos Costa | RM562156 |

## Sobre o Projeto

O **PetPath AI** é uma plataforma de gestão contínua da saúde de animais de estimação, desenvolvida como solução para o desafio proposto pela **CLYVO VET**. A API gerencia tutores, pets e consultas veterinárias com IA preditiva de saúde (Pet Health Score).

## Tecnologias

- Java 21
- Spring Boot 4.0.3
- Spring Data JPA + H2 Database
- Spring Security + JWT (jjwt 0.12.6)
- Spring Cache
- Bean Validation (Hibernate Validator)
- SpringDoc OpenAPI 3 (Swagger UI)
- JUnit 5 + Mockito

## Como Rodar

### Pré-requisitos
- Java 21+
- Maven 3.8+
- IntelliJ IDEA (recomendado)

### Passos

1. Clone ou extraia o projeto
2. Abra no IntelliJ: `File → Open → selecione a pasta petpath-java`
3. Aguarde o Maven baixar as dependências
4. Execute `PetpathApplication.java` clicando no botão ▶
5. Acesse: `http://localhost:8080/swagger-ui.html`

## Testando a API

### 1. Cadastre um Tutor (sem autenticação)
```
POST http://localhost:8080/tutores/cadastrar
{
  "nome": "Maria Silva",
  "cpf": "52998224725",
  "email": "maria@petpath.com",
  "telefone": "11999999999"
}
```

### 2. Cadastre um Usuário (sem autenticação)
```
POST http://localhost:8080/usuarios/novo
{
  "tutor": { "id": 1 },
  "rm": "rm000001",
  "senha": "123456",
  "permissao": "USER",
  "dataCriacao": "2025-01-01",
  "status": "ATIVO"
}
```

### 3. Faça Login
```
POST http://localhost:8080/autenticacao/login?usuario=rm000001&senha=123456&duracao=480
```
Copie o token retornado.

### 4. Autorize no Swagger
Clique em **Authorize 🔒** → digite `Bearer {seu_token}` → Authorize

### 5. Cadastre um Pet
```
POST http://localhost:8080/pets/cadastrar
{
  "tutor": { "id": 1 },
  "nome": "Rex",
  "especie": "CACHORRO",
  "raca": "Labrador",
  "data_nascimento": "2021-05-10",
  "peso": 15.5,
  "ativo": true
}
```

### 6. Agende uma Consulta
```
POST http://localhost:8080/consultas/agendar
{
  "pet": { "id": 1 },
  "data_consulta": "2025-06-20",
  "tipo": "PREVENTIVA",
  "descricao": "Vacinacao anual antirabica",
  "status": "AGENDADA"
}
```

## Endpoints Disponíveis

### Tutores
| Método | Endpoint | Descrição |
|---|---|---|
| GET | /tutores/todos | Lista todos os tutores |
| GET | /tutores/{id} | Busca tutor por ID |
| POST | /tutores/cadastrar | Cadastra novo tutor |
| PUT | /tutores/{id} | Atualiza tutor |
| DELETE | /tutores/{id} | Remove tutor |

### Pets
| Método | Endpoint | Descrição |
|---|---|---|
| GET | /pets/todos | Lista todos (com cache) |
| GET | /pets/paginados | Lista paginada e ordenada |
| GET | /pets/{id} | Busca por ID (com cache) |
| GET | /pets/buscar?substring= | Busca por substring (SQL nativo) |
| GET | /pets/raca?raca= | Busca por raça (Query Method) |
| GET | /pets/peso?peso= | Busca por peso mínimo (JPQL) |
| POST | /pets/cadastrar | Cadastra novo pet |
| PUT | /pets/{id} | Atualiza pet |
| DELETE | /pets/{id} | Remove pet |

### Consultas
| Método | Endpoint | Descrição |
|---|---|---|
| GET | /consultas/todas | Lista todas (com cache) |
| GET | /consultas/paginadas | Lista paginada |
| GET | /consultas/{id} | Busca por ID |
| GET | /consultas/por-status?status= | Filtra por status (JPQL) |
| GET | /consultas/por-data?data= | Filtra por data (SQL nativo) |
| POST | /consultas/agendar | Agenda consulta |
| PUT | /consultas/{id} | Atualiza consulta |
| DELETE | /consultas/{id} | Remove consulta |

### Autenticação
| Método | Endpoint | Descrição |
|---|---|---|
| POST | /autenticacao/login | Retorna token JWT |

## Banco de Dados

H2 em memória — console disponível em:
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:petpathdb
User: sa | Password: (vazio)
```

## Documentação Swagger
```
http://localhost:8080/swagger-ui.html
```
