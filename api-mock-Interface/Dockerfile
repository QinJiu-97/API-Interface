FROM maven:3.8.1-jdk-8-slim as builder
# copy local code to the container image
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY /target /app/target

# Build a release artifact
#RUN mvn package -DskipTests
EXPOSE 8123
# Run the web service on container startup
CMD ["java","-jar","/app/target/api-mock-Interface-0.0.1.jar"]