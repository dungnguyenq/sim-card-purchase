FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar secure-service.jar
ENTRYPOINT ["java","-jar","/secure-service.jar","secure-service"]
EXPOSE 8885