FROM openjdk:11-jdk-slim
WORKDIR /app
COPY target/CentralStoresEmployees-1.0.0.jar /app/central_stores_employees.jar
ARG JAR_FILE=*.jar
ENTRYPOINT ["java", "-jar", "central_stores_employees.jar"]