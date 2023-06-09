FROM openjdk:17-slim

RUN mkdir /app
WORKDIR /app

COPY . .

RUN apt-get update && \
    apt-get install -y findutils && \
    ./gradlew clean build -x test

RUN find build/libs -type f -name "*.jar" -exec mv {} app.jar \;

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
