FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar gallery-service.jar
ENTRYPOINT ["java","-jar","/gallery-service.jar","gallery-service"]
EXPOSE 8883