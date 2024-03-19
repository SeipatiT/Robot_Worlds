FROM debian:buster-slim

RUN apt-get update
RUN apt-get install -y openjdk-11-jre curl

ADD target/toyworld-1.1-SNAPSHOT-jar-with-dependencies.jar srv/toyworld-1.1-SNAPSHOT-jar-with-dependencies.jar

WORKDIR /srv
EXPOSE 5000
CMD ["java", "-jar", "toyworld-1.1-SNAPSHOT-jar-with-dependencies.jar"]