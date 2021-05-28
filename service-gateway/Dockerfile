FROM openjdk:13-jdk-alpine
VOLUME /tmp
EXPOSE 8443
COPY target/*.jar service-gateway.jar
ENTRYPOINT ["java","-jar","/service-gateway.jar"]
