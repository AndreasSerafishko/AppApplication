FROM eclipse-temurin:17-jre

ARG SERVER_PORT=8080
ARG PROFILE_DEV=true

ENV SERVER_PORT=${SERVER_PORT}
ENV PROFILE_DEV=${PROFILE_DEV}

EXPOSE ${SERVER_PORT}

WORKDIR /app

ADD target/demo-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${SERVER_PORT} --netology.profile.dev=${PROFILE_DEV}"]