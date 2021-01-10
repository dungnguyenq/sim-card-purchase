FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar voucher-service.jar
ENTRYPOINT ["java","-jar","/voucher-service.jar","voucher-service"]
EXPOSE 8883