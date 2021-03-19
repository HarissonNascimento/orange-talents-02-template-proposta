FROM openjdk:11

MAINTAINER Harisson do Nascimento Pires

ENV ANALISE="analise"
ENV CONTAS="contas"
ENV JAEGER="http://jaeger"
ENV URL_MYSQL="jdbc:mysql://mysql:3306/proposal-database?createDatabaseIfNotExist=true"
ENV KEYCLOAK_ISSUER_URI="http://keycloak:8080/auth/realms/proposal-realm"
ENV KEYCLOAK_JWKS_URI="http://keycloak:8080/auth/realms/proposal-realm/protocol/openid-connect/certs"

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "/application.jar"]
