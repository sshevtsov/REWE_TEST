FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/distributor-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8080

CMD ["java", "-jar", "distributor-0.0.1-SNAPSHOT.jar"]