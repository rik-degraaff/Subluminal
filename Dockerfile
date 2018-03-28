FROM openjdk:8u151-jre-alpine3.7
LABEL maintainer="Luc Kury <luc.kury@unibas.ch>"

WORKDIR /opt/lib/subluminal
ADD ./app/build/libs/Subluminal-0.3.0-beta1.jar /opt/lib/subluminal
EXPOSE 1790

ENTRYPOINT ["java", "-jar", "Subluminal-0.3.0-beta1.jar", "server", "1790"]