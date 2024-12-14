# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the local JAR file to the container
COPY target/Excel-0.0.1-SNAPSHOT.jar /app/Excel.jar

# Set environment variables for the application
ENV SPRING_APPLICATION_NAME=Excel
ENV LOGGING_LEVEL_ORG_SPRINGFRAMEWORK=DEBUG

# Set the environment variable for the application port (this is crucial for Render to bind the port correctly)
ENV PORT=8080

# Expose the application port (Render requires this to route traffic)
EXPOSE 8080

# Run the Spring Boot application, passing the PORT variable
CMD ["java", "-jar", "Excel.jar", "--server.port=${PORT}"]
