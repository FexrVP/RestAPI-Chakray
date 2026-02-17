# Etapa 1: compilacin con Maven
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: ejecución liviana
FROM eclipse-temurin:17-jre-alpine
LABEL maintainer="Diego Fernando Vazquez Perez <vazquez.diego.3007@gmail.com>" \
      version="1.0" \
      description="API REST Prueba Técnica Chakray"

WORKDIR /app
COPY --from=builder /build/target/PruebaTecnicaChakray-WS-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "app.jar"]