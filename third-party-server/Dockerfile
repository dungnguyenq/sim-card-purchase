FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar third-party-server.jar
ENTRYPOINT ["java","-jar","/third-party-server.jar","third-party-server"]
EXPOSE 9999