FROM maven:3.8.6-openjdk-11-slim AS build

WORKDIR /app

# Copiar archivos POM y fuente
COPY pom.xml .
COPY src ./src/

# Compilar y empaquetar el proyecto
RUN mvn clean package

# Imagen de ejecución
FROM openjdk:11-jre-slim

WORKDIR /app

# Copiar el JAR compilado desde la etapa de construcción
COPY --from=build /app/target/rabbitmq-java-example-1.0-SNAPSHOT.jar ./app.jar

# Configurar el comando de inicio para productor o consumidor (parametrizable)
ENTRYPOINT ["java", "-jar", "app.jar"]