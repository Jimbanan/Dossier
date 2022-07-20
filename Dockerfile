#./mvnw package && java -jar target/Dossier-0.0.1-SNAPSHOT.jar

FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8083
ARG JAR_FILE=target/Dossier-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]

#docker build -t Dossier:0.0.1 .