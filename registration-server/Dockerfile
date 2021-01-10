FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar registration-server.jar
ENTRYPOINT ["java","-jar","/registration-server.jar","registration"]
EXPOSE 8761