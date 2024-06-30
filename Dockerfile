# Stage 1: Build the application
FROM gradle:7.5.1-jdk17 AS builder
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build --no-daemon

# Stage 2: Run the application
FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app
COPY --from=builder /app/build/libs/BakingBuddy-0.0.1-SNAPSHOT.jar /app/BakingBuddy-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "BakingBuddy-0.0.1-SNAPSHOT.jar"]

LABEL image.name="baking-buddy-image"