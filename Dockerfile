#FROM amazoncorretto:17
FROM openjdk:17-jdk-alpine
ADD target/*.jar openweathermap-api.jar
# Make port 8080 available to the world outside this container
EXPOSE 8080
# Run the jar file
ENTRYPOINT ["java", "-jar", "openweathermap-api.jar"]
