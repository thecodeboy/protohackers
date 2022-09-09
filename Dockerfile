FROM eclipse-temurin:17
RUN mkdir /opt/app
COPY . /opt/app
WORKDIR /opt/app
EXPOSE 8080
CMD ["java", "EchoServer.java", "8080"]
