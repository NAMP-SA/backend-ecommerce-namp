FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
