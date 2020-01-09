FROM openjdk:8-jre-alpine

LABEL maintainer="frederic@fabre.io"

ENV JAR_FILE_NAME sensor-registration.jar
# Set the location of the verticles
ENV HOME /usr/sensor-registration

EXPOSE 9090

# Copy fat jar to the container
COPY target/$JAR_FILE_NAME $HOME/

# Launch the jar
WORKDIR $HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $JAR_FILE_NAME"]