FROM openjdk:13-jdk-alpine
VOLUME /tmp
EXPOSE 8761
COPY target/*.jar service-registry.jar
ENTRYPOINT ["java","-jar","/service-registry.jar"]