FROM openjdk:17
WORKDIR /app
EXPOSE 8081
COPY target/book-tracker-service-0.0.1-SNAPSHOT.jar book-tracker-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "book-tracker-service-0.0.1-SNAPSHOT.jar"]