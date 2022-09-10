FROM golang
RUN mkdir /opt/app
COPY . /opt/app
WORKDIR /opt/app
EXPOSE 10000
CMD ["go", "run", "main.go"]
