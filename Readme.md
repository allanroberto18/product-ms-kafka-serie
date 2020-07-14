## Micro Services with Kafka (Customer Micro Service - Part 3 of 5)

### Description

This part cover Spring Boot Web, Data, Cache, JUnit5, Mockito, MySQL and Redis that provide Products Micro Service.

### Requirements

Docker, Docker Compose

### Get start

1. Rename .env.dist to .env
2. Rename application.yaml.dist to application.yaml
3. If you are using Intellij Professional the .env values can be configured, if don't you can configure the values directly on application.yaml or set all as env values
4. Run command 

```
docker-compose -f stack-product.yaml
``` 

After run the application, you can check the available endpoints on http://localhost:6080/swagger-ui.html