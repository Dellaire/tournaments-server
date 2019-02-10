FROM java:8

ENV debugport $debugport
ENV profile $profile

WORKDIR /application
COPY tournament-server-0.0.1-SNAPSHOT.jar .

CMD java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=$debugport,suspend=n -Dspring.profiles.active=$profile -jar tournament-server.jar