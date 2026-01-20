# Multi-stage build for optimized Spring Boot Docker image

# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /build

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Create non-root user for security
RUN useradd -m -u 1000 appuser && \
    chown -R appuser:appuser /app

# Copy built JAR from builder stage
COPY --from=builder /build/target/*.jar app.jar
RUN chown appuser:appuser app.jar

# Set environment variables with defaults
ENV PORT=8080 \
    DB_URL=jdbc:mysql://mysql-service:3306/enterprisesystemengineering_db \
    DB_USER=root \
    DB_PASSWORD=password \
    JWT_SECRET=your-secret-key-minimum-32-characters-long-1234567890 \
    PYTHON_API_URL=http://python-service:8000 \
    LOG_LEVEL=INFO

# Expose port
EXPOSE ${PORT}

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://localhost:${PORT}/health/live || exit 1

# Switch to non-root user
USER appuser

# Run application with JVM optimizations for containers
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:InitialRAMPercentage=50.0", \
    "-Dspring.profiles.active=production", \
    "-jar", \
    "app.jar"]

