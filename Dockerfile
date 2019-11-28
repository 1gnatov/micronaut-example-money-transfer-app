FROM openjdk:11.0.5-jdk-stretch as builder
COPY * /tmp/
COPY gradle /tmp/gradle
COPY src /tmp/src
WORKDIR /tmp
RUN pwd
RUN ./gradlew build

FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.5_10 as runner
COPY --from=builder /tmp/build/libs/bank-*-all.jar bank.jar
EXPOSE 8900
CMD java -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar bank.jar