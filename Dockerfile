FROM maven:3-openjdk-11-slim AS build

RUN mkdir /build
WORKDIR /build

COPY pom.xml .
RUN mvn clean dependency:copy-dependencies

COPY . .
RUN mvn compile test package


FROM tomcat:9.0.52-jdk11-openjdk-slim-buster
ARG FILE=demo-0.0.1-SNAPSHOT.war

COPY .docker/tomcat /usr/local/tomcat
COPY --from=build /build/target/$FILE /usr/local/tomcat/webapps/ROOT.war
