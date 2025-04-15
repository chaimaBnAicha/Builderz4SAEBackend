FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar backend.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","backend.jar"]