FROM openjdk:11
WORKDIR /app
ARG JAR_FILE=target/desafio-proposta.jar
ENV LANG C.UTF-8
COPY ${JAR_FILE} app.jar
ENTRYPOINT java -jar /app/app.jar