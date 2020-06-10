FROM openjdk:8u121
MAINTAINER GhostBusters
WORKDIR /app
COPY ./target/administration-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "administration-0.0.1-SNAPSHOT.jar"]
EXPOSE 10000