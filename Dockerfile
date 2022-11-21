FROM eclipse-temurin:11
LABEL maintainer="yoonho"
WORKDIR /home/deploy/chatmgmt

ENV TZ="Asia/Seoul"
ENV LC_ALL=C.UTF-8
#ENV GC_OPTS="-XX:+UseG1GC -XX:MetaspaceSize=96m -Xms1024m -Xmx1024m"

COPY ./build/libs/*SNAPSHOT.jar /app.jar

ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]