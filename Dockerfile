# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the local code to the container
COPY target/Excel-0.0.1-SNAPSHOT.jar /app/Excel.jar

# Run the Spring Boot application
CMD ["java", "-jar", "Excel.jar"]
