FROM gcr.io/distroless/java:11

EXPOSE 8080

COPY target/*.jar /app/app.jar
WORKDIR /app

# Run as non root
USER 65532

CMD ["app.jar"]
