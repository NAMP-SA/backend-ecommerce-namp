# Etapa 1: Build
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app

# Copiamos sólo lo necesario para aprovechar el cache
COPY pom.xml mvnw .mvn/ ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src src
RUN ./mvnw clean package -DskipTests -B

# Etapa 2: Runtime
FROM eclipse-temurin:17-jdk-slim
WORKDIR /app

# Copiamos el artefacto construido
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
