FROM amazoncorretto:21.0.6

WORKDIR /opt/sandbox/elasticache-client

COPY elasticache-client.jar elasticache-client.jar

ENTRYPOINT [ "java", "-jar", "elasticache-client.jar" ]