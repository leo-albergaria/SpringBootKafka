# **📦 Sistema de Pedidos \- Spring Boot & Apache Kafka**

Um microserviço robusto desenvolvido com **Java** e **Spring Boot**, focado no processamento de pedidos em tempo real utilizando o **Apache Kafka** como mensageria assíncrona.

## ---

**🚀 Tecnologias Utilizadas**

> * **Java 17 / 21**  
> * **Spring Boot 3.3.x**  
  * Spring Web  
  * Spring Kafka  
> * **Apache Kafka** & **Zookeeper / KRaft**  
> * **Maven** (Gerenciador de dependências)

## ---

**🏛️ Arquitetura do Projeto**

O projeto utiliza o Apache Kafka para garantir o desacoplamento e a comunicação assíncrona entre o serviço de criação de pedidos e os consumidores downstream (como serviços de pagamento, estoque ou notificação).  
`[ Cliente / HTTP ] ──> [ Controller / Pedidos ] ──> [ Kafka Producer ]`  
                                                            `│`  
                                                            `▼`  
                                                     `[ Tópico Kafka ]`  
                                                            `│`  
                                                            `▼`  
                                                    `[ Kafka Consumer ]`

## ---

**🛠️ Como Executar o Projeto Localmente**

### **Pré-requisitos**

> * **Java JDK 17+** instalado  
> * **Maven** instalado (ou utilizar o wrapper ./mvnw)  
> * Instância do **Apache Kafka** em execução localmente (ou via Docker)

### **1\. Clonar o repositório**

`git clone https://github.com/SeuUsuario/seu-repositorio.git`  
`cd seu-repositorio`

### **2\. Configurar o Apache Kafka**

Garanta que o cluster Kafka esteja rodando localmente na porta padrão 9092 (ou ajuste as configurações no arquivo application.properties / application.yml).  
Exemplo de subida via Docker Compose (se aplicável):  
`docker-compose up -d`

### **3\. Compilar e Executar a Aplicação**

`# Compilar o projeto`  
`./mvnw clean package`

`# Executar a aplicação`  
`./mvnw spring-boot:run`  
A aplicação estará acessível em http://localhost:8080.

## ---

**🔌 Endpoints Principais**

| Método | Endpoint | Descrição   |
| :---- | :---- | :---- |
| POST | /api/pedidos | Envia um novo pedido para o tópico do Kafka |
| GET | /api/pedidos | Lista os pedidos (caso aplicável) |

### **Exemplo de Payload (POST /api/pedidos)**

`{`  
  `"id": "12345",`  
  `"cliente": "João Silva",`  
  `"valorTotal": 150.50,`  
  `"itens": [`  
    `{`  
      `"produto": "Teclado Mecânico",`  
      `"quantidade": 1,`  
      `"preco": 150.50`  
    `}`  
  `]`  
`}`

## ---

**✒️ Autor**

Desenvolvido por **\[Seu Nome\]** — sinta-se à vontade para entrar em contato ou conectar-se no [LinkedIn](https://linkedin.com)\!