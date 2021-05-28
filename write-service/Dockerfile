FROM openjdk:13-jdk-alpine
VOLUME /tmp
EXPOSE 6003
COPY target/*.jar write-sevice.jar
ENTRYPOINT ["java","-jar","/write-sevice.jar"]