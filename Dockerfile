FROM openjdk:17-jdk-alpine
COPY target/G_ETABLISSEMENT-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]