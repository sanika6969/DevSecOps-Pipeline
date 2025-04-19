# Stage 1: Build the Java app (only needed if you don't have the JAR file)
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Use a minimal secure base image for running Java
FROM gcr.io/distroless/java17-debian12

# Set application directory
WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application securely
CMD ["app.jar"]
