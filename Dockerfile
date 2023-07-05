FROM amazoncorretto:17-alpine-jdk
EXPOSE 8080
COPY . .
CMD ["./gradlew", "bootRun", "--parallel", "--build-cache"]
