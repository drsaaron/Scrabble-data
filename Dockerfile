FROM drsaaron/blazarjavabase:1.73

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

# run the script
CMD [ "sh", "-c", "./runServices.sh" ]

