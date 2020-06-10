FROM openjdk:8u121
MAINTAINER GhostBusters
WORKDIR /app
COPY ./target/paymentservice-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "paymentservice-0.0.1-SNAPSHOT.jar"]
EXPOSE 8083