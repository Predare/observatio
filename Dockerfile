# syntax=docker/dockerfile:1

FROM openjdk:17-alpine3.13 as base

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src

FROM base as test
RUN ["./mvnw", "test"]

FROM base as development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

FROM base as build
RUN ./mvnw package

FROM openjdk:jre-slim as production
EXPOSE 8080

COPY --from=build /app/target/spring-observatio-*.jar /spring-observatio.jar

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/spring-observatio.jar"]
