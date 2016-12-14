FROM openjdk:8-jdk-alpine

RUN apk add --update bash && rm -rf /var/cache/apk/*