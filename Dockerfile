FROM amazoncorretto:17
ADD target/*.jar openweathermap-api.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "openweathermap-api.jar"]
