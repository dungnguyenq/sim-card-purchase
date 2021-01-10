FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar sms-service.jar
ENTRYPOINT ["java","-jar","/sms-service.jar","sms-service"]
EXPOSE 8884