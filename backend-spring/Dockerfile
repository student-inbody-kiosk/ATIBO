FROM bellsoft/liberica-openjdk-alpine:17

WORKDIR /app

COPY build/libs/backend-spring.jar /app/backend-spring.jar

COPY src/main/resources/static/logo/default-logo-image.png /src/main/resources/static/logo/default-logo-image.png

EXPOSE 8080

CMD ["java", "-jar", "backend-spring.jar"]