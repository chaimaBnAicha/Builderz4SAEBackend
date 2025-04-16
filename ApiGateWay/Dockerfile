FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/ApiGateWay-0.0.1-SNAPSHOT.jar api-gateway.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
EXPOSE 8093
