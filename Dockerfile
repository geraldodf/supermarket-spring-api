FROM openjdk:17
ADD target/supermarket-api.1.0.jar app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
