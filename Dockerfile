FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
ARG MAVEN_PROFILE=dev
RUN mvn clean package -P ${MAVEN_PROFILE} -DskipTests && \
    mv target/*.jar target/backend.jar

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/backend.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
