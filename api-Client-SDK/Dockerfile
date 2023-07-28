FROM maven:3.8.1-jdk-8-slim as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
#COPY /target/user-center-0.0.1-SNAPSHOT.jar /app/target/user-center-0.0.1-SNAPSHOT.jar

# Build a release artifact
RUN mvn install -DskipTests

# Run the web service on container startup
#CMD ["java","-jar","/app/target/api-Client-SDK-0.0.1.jar","--spring.profiles.active=prod"]