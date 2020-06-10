FROM openjdk:8u121
MAINTAINER GhostBusters
WORKDIR /app
COPY ./zipkin-server-2.12.9-exec.jar /app/
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "zipkin-server-2.12.9-exec.jar"]
EXPOSE 9411