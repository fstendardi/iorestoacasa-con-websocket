FROM openjdk:8-jdk-alpine


ARG JAR_FILE
ADD target/app.jar app.jar

CMD java $JAVA_OPTS -jar /app.jar --server.port=$PORT
