# 📦 Sistema de Pedidos com Spring Boot e Apache Kafka

Um microserviço desenvolvido em **Java** com **Spring Boot**, projetado para o processamento assíncrono de pedidos utilizando **Apache Kafka** como plataforma de mensageria. A aplicação demonstra uma arquitetura desacoplada baseada em eventos, permitindo escalabilidade e comunicação eficiente entre serviços.

---

# 🚀 Tecnologias Utilizadas

- **Java 17 / 21**
- **Spring Boot 3.3.x**
    - Spring Web
    - Spring Kafka
- **Apache Kafka**
- **Apache Zookeeper** ou **KRaft**
- **Maven**

---

# 🏗️ Arquitetura

A aplicação segue uma arquitetura orientada a eventos (*Event-Driven Architecture*), onde o serviço responsável pela criação de pedidos publica mensagens em um tópico Kafka. Outros microsserviços podem consumir esses eventos para executar tarefas como processamento de pagamentos, controle de estoque ou envio de notificações.

```text
Cliente (HTTP)
      │
      ▼
Controller (Pedidos)
      │
      ▼
Kafka Producer
      │
      ▼
+----------------+
|  Tópico Kafka  |
+----------------+
      │
      ▼
Kafka Consumer
      │
      ├── Serviço de Pagamentos
      ├── Serviço de Estoque
      └── Serviço de Notificações
```

---

# ⚙️ Como Executar o Projeto

## Pré-requisitos

- Java JDK 17 ou superior
- Maven (ou Maven Wrapper)
- Apache Kafka em execução
- Docker (opcional)

## 1. Clonar o repositório

```bash
git clone https://github.com/SeuUsuario/seu-repositorio.git
cd seu-repositorio
```

## 2. Iniciar o Kafka

Certifique-se de que o Kafka esteja disponível na porta **9092**.

Caso utilize Docker Compose:

```bash
docker-compose up -d
```

Se necessário, ajuste as configurações no arquivo:

```properties
application.properties
```

ou

```yaml
application.yml
```

---

## 3. Compilar o projeto

```bash
./mvnw clean package
```

---

## 4. Executar a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

---

# 📡 API

## Criar Pedido

**POST** `/api/pedidos`

Publica um novo pedido no tópico Kafka.

### Exemplo de requisição

```json
{
  "id": "12345",
  "cliente": "João Silva",
  "valorTotal": 150.50,
  "itens": [
    {
      "produto": "Teclado Mecânico",
      "quantidade": 1,
      "preco": 150.50
    }
  ]
}
```

### Resposta esperada

```json
{
  "mensagem": "Pedido enviado para processamento."
}
```

---

## Listar Pedidos

**GET** `/api/pedidos`

Retorna a lista de pedidos (caso implementado).

---

# 📂 Estrutura do Projeto

```text
src
├── main
│   ├── java
│   │   └── com
│   │       └── exemplo
│   │           └── pedidos
│   │               ├── config
│   │               ├── controller
│   │               ├── consumer
│   │               ├── dto
│   │               ├── model
│   │               ├── producer
│   │               ├── service
│   │               └── PedidoApplication.java
│   └── resources
│       ├── application.properties
│       └── application.yml
└── test
```

---

# 🔄 Fluxo de Processamento

1. O cliente envia uma requisição HTTP.
2. O Controller recebe o pedido.
3. O Producer publica o evento no Kafka.
4. O pedido é armazenado em um tópico.
5. Os Consumers processam a mensagem de forma assíncrona.
6. Outros microsserviços podem consumir o mesmo evento sem alterar a aplicação principal.

---

# 🎯 Objetivos do Projeto

- Demonstrar comunicação assíncrona utilizando Apache Kafka.
- Aplicar boas práticas de desenvolvimento com Spring Boot.
- Implementar uma arquitetura baseada em eventos.
- Servir como base para estudos de microsserviços.
- Demonstrar integração entre Producer e Consumer.

---

# 📚 Conceitos Demonstrados

- Comunicação assíncrona
- Mensageria
- Apache Kafka
- Event-Driven Architecture (EDA)
- Producer e Consumer
- REST API
- Spring Boot
- Injeção de Dependências
- Serialização de Objetos

---

# 👨‍💻 Autor

Desenvolvido para fins de estudo e demonstração de conhecimentos em **Spring Boot**, **Apache Kafka** e arquitetura de microsserviços.

---

# 📄 Licença

Este projeto está licenciado sob a licença MIT. Sinta-se à vontade para utilizar como referência para estudos.