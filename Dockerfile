FROM openjdk:17

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY jsend-network-1.0-SNAPSHOT.jar ./

COPY src ./src
EXPOSE 8081
CMD ["./mvnw", "spring-boot:run"]

