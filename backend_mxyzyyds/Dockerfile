#FROM maven:3-openjdk-11 as build-stage
#WORKDIR /app
#COPY ./ .
#RUN mvn install -DskipTests

FROM openjdk:8 as production-stage
# ENV db_pass='' db_hostname='10.0.16.9'
COPY ./target/ /target/
CMD java -jar target/collect*.jar
