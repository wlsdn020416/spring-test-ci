FROM gradle:jdk17 AS builder
WORKDIR /workspace
COPY build.gradle setting.gradle ./
RUN gradle dependencies --no-daemon || true
COPY src src
RUN gradle bootJar --no-daemon

FROM azul/zulu-openjdk-alpine:17-jre-headless-latest
WORKDIR /app
COPY --from=builder /workspace/build/libs/*-SNAPSHOT.jar app.jar
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]