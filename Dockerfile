FROM eclipse-temurin:17-jdk-focal AS builder

WORKDIR /app

# Copy the wrapper and pom from BackendAPI
COPY BackendAPI/mvnw .
COPY BackendAPI/.mvn .mvn
COPY BackendAPI/pom.xml .

# Give execution permission to maven wrapper
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY BackendAPI/src src

# Build the project
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
