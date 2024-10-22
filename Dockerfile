# For Java 11
FROM openjdk:latest

# create temporary drive
VOLUME /tmp

# expose ports 8080 for spring boot
EXPOSE 8080

# create app dir
RUN mkdir -p /app/

# create logs dir
RUN mkdir -p /app/logs/

# cp demo-0.0.1-SNAPSHOT.jar /app/application.jar
ADD target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

# run the .jar file with the active profile dev
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev", "-jar", "/app/app.jar"]