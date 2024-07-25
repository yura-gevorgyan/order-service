FROM openjdk:17-jdk
WORKDIR /app
COPY target/order-service-0.0.1-SNAPSHOT.jar /app/order-service.jar
CMD ["java", "-jar", "/app/order-service.jar"]