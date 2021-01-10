FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar zuul-server.jar
ENTRYPOINT ["java","-jar","/zuul-server.jar"]