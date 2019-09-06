# Votação API

## Pre-requisitos

PostgreSQL

Kafka

## Instalação

### POSTGRES
### pull
```
docker pull postgres
```
### run
```
sudo docker run -p 5432:5432 --name postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -d postgres:11.1-alpine
```

### KAFKA
### pull
```
docker pull spotify/kafka
```
### run
```
docker run -d -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=localhost --env ADVERTISED_PORT=9092 --env KAFKA_ADVERTISED_HOST_NAME=localhost --env KAFKA_ADVERTISED_PORT=9092 --name kafka spotify/kafka
```
### new topic
```
docker exec kafka /opt/kafka_2.11-0.10.1.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic votacao
```
### new producer
```
docker run -it --rm --link kafka spotify/kafka /opt/kafka_2.11-0.10.1.0/bin/kafka-console-producer.sh --broker-list kafka:9092 --topic votacao
```
### new consumer
```
docker run -it --rm --link kafka spotify/kafka /opt/kafka_2.11-0.10.1.0/bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic votacao --from-beginning
```

### SWAGGER
```
http://localhost:8080/api-votacao/swagger-ui.html
```

### ACTUATOR
```
http://localhost:8080/api-votacao/actuator
```
```
http://localhost:8080/api-votacao/actuator/health
```
## Desenvolvido com:

* [Springboot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) - The web framework used
* [Apache Camel](https://camel.apache.org/documentation.html) - The Route based framework
* [Lombok](https://projectlombok.org/features/all) - The build tools framework
* [Hibernate](https://hibernate.org/orm/documentation/5.4/) - The Object/Relational Mapping (ORM) framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [PostgreSQL](https://www.postgresql.org/) - PostgreSQL
* [Swagger](https://swagger.io/docs/) - The API design framework
* [Kafka](https://kafka.apache.org/) - Apache Kafka


## Autores

* **Leonardo Chassot** - *Initial work* - [leonardo-ch](https://github.com/leonardo-ch)

