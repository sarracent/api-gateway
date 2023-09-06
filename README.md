# API Gateway

**Nombre del servicio**

`api-gateway`

**Puerto**

`8765`

**Objetivo**

Con el objetivo de garantizar la seguridad del sistema se decide implementar una API Gateway que permita la validación de tokens, 
evitando un ByPass, por los tanto las peticiones hacia el servicio de precios tienen que tener un Bearer Token.

* Nota: las peticiones hacia el servicio de precios no tiene que ser necesariamente a través de la API Gateway, 
 pero el mismo quedaría expuesto ante cualquier ataque de seguridad

# Diseño de Solución

* Se diseña la solución basada en 3 servicios, una API Gateway responsable de gestionar todo el tráfico que interactúa con los servicios y
  validar el Bearer token de las peticiones.

* authentication-service responsable de la generación de tokens con JWT.

* price-service, responsable de obtener los precios según los parámetros de entrada:

![Diseño de solución](https://github.com/sarracent/api-gateway/blob/main/arquitectura.png)

**Pasos para ejecutar el servicio**

* `mvn clean install`

* `mvn spring-boot:run`

* Ejecutar peticiones


  **Ejemplo de peticiones**

* **Petición para registrar usuario** (No necesita autenticación)

````
    curl --location 'http://localhost:8765/auth/register' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "name": "Damian",
            "email": "damian@resolvit.com",
            "password": 123
        }'
````   

* **Petición para generar token** (No necesita autenticación)

````
    curl --location 'http://localhost:8765/auth/token' \
        --header 'Content-Type: application/json' \
        --data '{
            "username": "Damian",
            "password": "123"
        }'
````   

* **Petición validar token** (No necesita autenticación)

````
    curl --location 'http://localhost:8765/auth/validate?
        token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYW1pYW4iLCJpYXQiOjE2OTM5Njk2MjksImV4cCI6MTY5Mzk3MTQyOX0.o7DowSd2xUnElJjxBgv1QHDVOEiQejgy8JMDZTa1OrA'
````

**Ejemplo de peticiones** 

* **Test 1: Request at 10:00 AM on Day 14 of Product 35455 for Brand 1 (ZARA)** (Se Pasa Bearer Token)

````
    curl --location 'http://localhost:8765/api/prices?productId=35455&brandId=1&applicationDate=2020-06-14T10:00:00' \
    --header 'accept: application/json' \
    --header 'Service-Id: price-service' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYW1pYW4iLCJpYXQiOjE2OTM5NzAwMzgsImV4cCI6MTY5Mzk3MTgzOH0.EbCQeaSoq-2Y0paZqjf9rcIYLwS9cmoVTvLVR3uVozs'
````

* **Test 2: Request at 4:00 PM on Day 14 of Product 35455 for Brand 1 (ZARA)** (Se Pasa Bearer Token)

````
    curl --location 'http://localhost:8765/api/prices?applicationDate=2020-06-14T16:00:000&productId=35455&brandId=1' \
    --header 'service-id: prices-service' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYW1pYW4iLCJpYXQiOjE2OTM5NzAwMzgsImV4cCI6MTY5Mzk3MTgzOH0.EbCQeaSoq-2Y0paZqjf9rcIYLwS9cmoVTvLVR3uVozs'
````

* **Test 3: Request at 9:00 PM on Day 14 of Product 35455 for Brand 1 (ZARA)** (Se Pasa Bearer Token)

````
    curl --location 'http://localhost:8765/api/prices?applicationDate=2020-06-14T21:00:00&productId=35455&brandId=1' \
    --header 'service-id: prices-service' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYW1pYW4iLCJpYXQiOjE2OTM5NzAwMzgsImV4cCI6MTY5Mzk3MTgzOH0.EbCQeaSoq-2Y0paZqjf9rcIYLwS9cmoVTvLVR3uVozs'
````

* **Test 4: Request at 10:00 AM on Day 15 of Product 35455 for Brand 1 (ZARA)** (Se Pasa Bearer Token)

````
    curl --location 'http://localhost:8765/api/prices?applicationDate=2020-06-15T10:00:00&productId=35455&brandId=1' \
    --header 'service-id: prices-service' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYW1pYW4iLCJpYXQiOjE2OTM5NzAwMzgsImV4cCI6MTY5Mzk3MTgzOH0.EbCQeaSoq-2Y0paZqjf9rcIYLwS9cmoVTvLVR3uVozs'
````

* **Test 5: Request at 9:00 PM on the 16th of Product 35455 for Brand 1 (ZARA)** (Se Pasa Bearer Token)

````
    curl --location 'http://localhost:8765/api/prices?applicationDate=2020-06-16T21:00:00&productId=35455&brandId=1' \
    --header 'service-id: prices-service' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEYW1pYW4iLCJpYXQiOjE2OTM5NzAwMzgsImV4cCI6MTY5Mzk3MTgzOH0.EbCQeaSoq-2Y0paZqjf9rcIYLwS9cmoVTvLVR3uVozs'
````

