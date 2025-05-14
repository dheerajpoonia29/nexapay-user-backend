FROM openjdk:17-jdk-slim

ENV APP_HOME=/app
ENV DATASOURCE_USERNAME=root
ENV DATASOURCE_PASSWORD=password

WORKDIR $APP_HOME

COPY target/nexapay-user-backend-0.0.1-SNAPSHOT.jar .

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "nexapay-user-backend-0.0.1-SNAPSHOT.jar"]