FROM gradle:7.6.2-jdk17 AS builder

WORKDIR /app

COPY --chown=gradle:gradle . .

RUN ./gradlew bootjar

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
