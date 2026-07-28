# Start from an official Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/*.jar app.jar

# Run the jar when container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
