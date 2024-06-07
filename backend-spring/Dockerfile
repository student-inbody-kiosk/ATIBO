FROM bellsoft/liberica-openjdk-alpine:17

WORKDIR /app

COPY build/libs/backend-spring-0.0.1-SNAPSHOT-plain.jar backend-spring.jar

CMD ["java", "-jar", "backend-spring.jar"]