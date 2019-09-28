FROM openjdk:11.0.4-jdk

VOLUME /tmp

WORKDIR /opt

COPY ./target/demo-0.0.1-SNAPSHOT.jar /opt/app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]