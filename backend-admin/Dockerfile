FROM openjdk:13-jdk-alpine
VOLUME /tmp
EXPOSE 1001
COPY target/*.jar admin-service.jar
ENTRYPOINT ["java","-jar","/admin-service.jar"]