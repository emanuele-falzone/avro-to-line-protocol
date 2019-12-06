FROM maven:3.6-jdk-8 AS BUILD

WORKDIR /app

COPY pom.xml pom.xml

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package assembly:single

FROM openjdk:8u232

COPY run.sh /app/run.sh

RUN chmod +x /app/run.sh

COPY --from=BUILD /app/target/avro-to-line-protocol-1.0-SNAPSHOT-jar-with-dependencies.jar /app/magic.jar

CMD /app/run.sh