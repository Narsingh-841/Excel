# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the local code to the container
COPY target/Excel-0.0.1-SNAPSHOT.jar /app/Excel.jar

# Set the application name and log level as environment variables
ENV SPRING_APPLICATION_NAME=Excel
ENV LOGGING_LEVEL_ORG_SPRINGFRAMEWORK=DEBUG

# Tell the application to listen on the correct port
ENV PORT=8080

# Run the Spring Boot application
CMD ["java", "-jar", "Excel.jar", "--server.port=${PORT}"]
