FROM adoptopenjdk/openjdk11
MAINTAINER Cyril Marchive <cyril.marchive@gmail.com>
ARG JAR_FILE=ms-launcher/target/ms-launcher-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} tondeuse.jar
EXPOSE 8080
ENTRYPOINT ["/opt/java/openjdk/bin/java"]
CMD ["-jar", "/tondeuse.jar"]