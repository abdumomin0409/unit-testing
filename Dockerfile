# Asosiy Docker obraz
# Ishga tushirish katalogi
FROM ubuntu:22.04 as production
RUN apk update && apk add tzdata

ENV TZ=Asia/Tashkent

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app
LABEL authors="abdumomin"

RUN apt-get update && apt-get install -y python-software-properties software-properties-common
RUN add-apt-repository ppa:webupd8team/java

RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 boolean true" | debconf-set-selections

RUN apt-get update && apt-get install -y oracle-java8-installer maven

ADD . /com/coreteam/languageservice
RUN cd /com/coreteam/languageservice && mvn assembly:assembly
CMD ["java", "-cp", "/language-service/target/language-service-jar-with-dependencies.jar", "com.coreteam.languageservice.LanguageServiceApplication"]


COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

# FROM base as test
# CMD ["./mvnw", "test"]

FROM base as development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=dev", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

FROM base as build
RUN ./mvnw package


EXPOSE 8093
COPY --from=build /target/language-service.jar /spring-petclinic.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/spring-petclinic.jar"]
