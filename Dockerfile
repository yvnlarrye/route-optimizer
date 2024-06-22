FROM openjdk:17
WORKDIR /usr/src/app/
COPY ./target/route-optimizer-0.0.1-SNAPSHOT.jar ./route-optimizer.jar
EXPOSE 8081
ENV TZ="Europe/Moscow"
CMD ["java", "-jar", "route-optimizer.jar"]