FROM openjdk:8u121
MAINTAINER GhostBusters
WORKDIR /app
COPY ./target/discoveryservice-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "discoveryservice-0.0.1-SNAPSHOT.jar"]
EXPOSE 8761