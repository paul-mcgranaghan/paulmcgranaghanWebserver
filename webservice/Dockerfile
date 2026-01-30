# For Java 11, try this
FROM azul/zulu-openjdk:21.0.10-21.48-jre-headless-x86
COPY ./build/libs/webservice.jar webservice.jar
CMD ["java","-jar","webservice.jar"]

EXPOSE 8090
