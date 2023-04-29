# SpringBoot Simple Application

### This project includes : 
- Hibernate
- H2
- JPA
- Spring Security
- Spring Data
- Swagger
- Log4j2
- MapStruct
- Elastic Search
---
### Installation and Information :

This project has been dockerized and you should :

```
docker-compose up -d
```

Then You can run project and enter : `https://localhost:8081/console` for visit H2 Dashboard console . 

H2 And JDBC Credentials :

```
Driver : org.h2.Driver
JDBC URL : jdbc:h2:file:~/elyasdb
Username : admin
Password : admin
```

You can also change this credentials inside `src/main/resources/application.properties` file .

### Swagger documentation : 
You can visit and use swagger documentation at : `http://localhost:8081/api-docs`

You can visit and use swagger UI at : `https://localhost:8081/swagger-ui/`


### Kibana dashboard (for elastic) : 
`http://localhost:5601`

Choose "Discover" and create index pattern with `error*` name .

---



