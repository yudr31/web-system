FROM openjdk:8
MAINTAINER yuderen yuderen6312@163.com

COPY web-system-1.0-SNAPSHOT.war /home/web-system.war

WORKDIR /home

EXPOSE 9200

CMD ["java", "-jar", "web-system.war"]
