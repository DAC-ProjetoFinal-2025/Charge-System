FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN apk update && apk add --no-cache postgresql-client curl

COPY target/*.jar /app/charge-manager.jar

EXPOSE 8085

CMD ["java", "-jar", "/app/charge-manager.jar"]