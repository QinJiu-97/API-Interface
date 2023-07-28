FROM maven:3.8.1-jdk-8-slim as builder
# copy local code to the container image
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY /target /app/target


