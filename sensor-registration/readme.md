#### Run the registration service in dev mode
``
mvn spring-boot:run
``
#### Build a Docker image and run a container
``
docker build -t sensor-registration .
``
<br />
``
docker run --rm -p 9090:9090 --name=sensor-registration sensor-registration
``

### Swagger
``
http://localhost:9090/swagger-ui.html
``

