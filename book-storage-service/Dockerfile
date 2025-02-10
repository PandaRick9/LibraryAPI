FROM openjdk:17-alpine
WORKDIR /app
COPY target/book-storage-service-3.3.5.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]