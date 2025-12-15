FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn package -DskipTests


FROM eclipse-temurin:21-jdk-alpine 
COPY --from=build target/chargeManagerApp.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]