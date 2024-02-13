FROM mcr.microsoft.com/java/jre:17-zulu-alpine

# Refer to Maven build -> finalName
ARG JAR_FILE=target/supply-reward-engine-1.0.0.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

EXPOSE 80

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${ENV}", "app.jar"]