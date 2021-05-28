FROM openjdk:13-jdk-alpine
VOLUME /tmp
EXPOSE 7003
COPY target/*.jar read-sevice.jar
ENTRYPOINT ["java","-jar","/read-sevice.jar"]