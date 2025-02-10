FROM openjdk:17
WORKDIR /app
EXPOSE 8085
COPY target/SecurityService-0.0.1-SNAPSHOT.jar SecurityService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "SecurityService-0.0.1-SNAPSHOT.jar"]