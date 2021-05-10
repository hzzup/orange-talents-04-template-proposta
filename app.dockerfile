FROM openjdk:11 AS base
ENV LANG C.UTF-8
WORKDIR /app

FROM maven:3.6.3-jdk-11 AS build
WORKDIR /src
COPY ./ /src

RUN mvn -f /src/pom.xml clean install -U -C -DskipTests

RUN rm -f /src/target/*sources.jar
RUN ls -lah /src/target
RUN cp -a /src/target/desafio-proposta.jar /app.jar

FROM base AS final
COPY --from=build /app.jar .
EXPOSE 8080
ENTRYPOINT java -jar /app/app.jar