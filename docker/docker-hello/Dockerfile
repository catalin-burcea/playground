FROM openjdk:8-jdk-alpine
MAINTAINER cburcea
WORKDIR /opt/docker/hello
ARG JAR_FILE=target/docker-hello-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} docker-hello.jar
ENTRYPOINT ["java","-jar","docker-hello.jar"]
#CMD ["-jar", "/opt/docker/docker-hello.jar"]
EXPOSE 8888

# mvn clean install
# docker build --tag=cburcea/hello:0.0.1-SNAPSHOT --rm=true .
# docker rm --force hello_container
# docker run --name=hello_container --publish=8080:8080--detach cburcea/hello:0.0.1-SNAPSHOT
# docker ps [--all]
# docker logs hello_container
# docker images
