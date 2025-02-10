FROM openjdk:17
WORKDIR /app
EXPOSE 8080
COPY target/book-storage-service-0.0.1-SNAPSHOT.jar book-storage-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "book-storage-service-0.0.1-SNAPSHOT.jar"]