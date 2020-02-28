FROM openjdk:8-jre-alpine

LABEL maintainer="frederic@fabre.io"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]