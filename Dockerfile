FROM openjdk:8-jdk-alpine

LABEL maintainer="Hemant Bhagwani <hemant5171@gmail.com>"

USER root

RUN useradd appusr \
           --shell /bin/bash \
		   --create-home \
&& usermod -a -G sudo appusr \
&& echo 'ALL ALL -(ALL) NOPASSWD: ALL' >> /etc/sudoers \
&& echo 'appusr:secret' | chpasswd

ENV HOME=/home/appusr

COPY docker-entrypoint.sh $HOME/

COPY target/*.jar $HOME/fantasy-league.jar

WORKDIR $HOME/

USER appusr

ENTRYPOINT ["docker-entrypoint.sh"]