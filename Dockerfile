FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/star-two-three-0.0.1-SNAPSHOT.jar app.jar

COPY .env .
COPY .env /app/


ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

