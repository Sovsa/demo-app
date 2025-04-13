FROM maven:3.9.7-eclipse-temurin-21 AS build
WORKDIR /home/demo-app
COPY pom.xml .
RUN mvn verify --fail-never
COPY src ./src
RUN mvn package -Dmaven.test.skip=true
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/demo-app/target/demo-0.0.1-SNAPSHOT.jar"]