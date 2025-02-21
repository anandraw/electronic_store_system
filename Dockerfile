FROM openjdk:21
WORKDIR /appContainer
COPY  ./target/elestorejar.jar /appContainer
EXPOSE 8282
CMD [ "java","-jar","elestorejar.jar" ]