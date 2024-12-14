# Stage 1: Build JAR
FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create Final Image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/Excel-0.0.1-SNAPSHOT.jar /app/Excel.jar
ENV SPRING_APPLICATION_NAME=Excel
ENV LOGGING_LEVEL_ORG_SPRINGFRAMEWORK=DEBUG
ENV PORT=8080
EXPOSE 8080
CMD ["java", "-jar", "Excel.jar", "--server.port=${PORT}"]
