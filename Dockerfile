# Use a lightweight OpenJDK 17 base image for Spring Boot 3.5.0
FROM eclipse-temurin:17-jdk-alpine

# Set environment variables (non-sensitive)
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/smarttask-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on (Spring Boot default is 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
