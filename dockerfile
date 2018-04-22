FROM openjdk:8
ADD target/authentication-service-0.0.1-SNAPSHOT.jar authentication-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "jsoneditor.jar"]