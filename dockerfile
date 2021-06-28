FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY template/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]
