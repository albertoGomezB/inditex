# Using Eclipse Temurin 21 JDK Alpine as base image
FROM eclipse-temurin:21-jdk-alpine

# Working directory
WORKDIR /app

# Copy the Maven build artifact-*
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
