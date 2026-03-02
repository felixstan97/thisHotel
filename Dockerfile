# Stage 1: build
FROM eclipse-temurin:17-jdk as build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw -DskipTests package

# Stage 2: runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
