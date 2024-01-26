# Build Phase
FROM gradle:8-jdk17-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle settings.gradle /app/

# Copy the source code
COPY src /app/src

# Build the application without using the daemon
RUN gradle --no-daemon build

# Runtime Phase
FROM eclipse-temurin:17.0.9_9-jre-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy only the JAR file from the build phase
COPY --from=builder /app/build/libs/axon_basic-1.0.0.jar /app/axon_basic-1.0.0.jar

EXPOSE 8080

# Run the JAR file when the container starts
CMD ["java", "-jar", "axon_basic-1.0.0.jar"]
