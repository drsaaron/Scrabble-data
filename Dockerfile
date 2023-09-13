FROM drsaaron/blazarjavabase:1.26

ENV ENVIRONMENT=prod

# add the source directory and mvn stuff
ADD ./pom.xml ./pom.xml
ADD ./src ./src
ADD ./mvnw ./mvnw
ADD ./.mvn ./.mvn

# build.  set skip.it to bypass the openapi spec generation
RUN mvnw clean install -Dskip.it=true

# add a shell script to run the java program
ADD ./runServices.sh ./runServices.sh

# add a healthcheck
ENV SERVER_PORT=4100
HEALTHCHECK CMD curl --silent --fail http://localhost:$SERVER_PORT/monitoring/health || exit 1

# run the script
CMD ./runServices.sh

