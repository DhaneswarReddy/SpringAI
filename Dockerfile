#Building the application
FROM eclipse-temurin:21-jdk-jammy as builder
WORKDIR /app
COPY . /app
RUN ./mvnw clean package -DskipTests

#Running the application
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"] 