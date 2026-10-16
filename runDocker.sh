#! /bin/sh

# run the service
imageName=$(dockerImageName.sh)
imageVersion=$(getPomAttribute.sh version | sed 's/-RELEASE$//')
containerName=$(echo $imageName | sed -re 's%^.*/([a-zA-Z]*)$%\1%') # could also use basename $(dockerImageName.sh)

port=$(grep ^server.port src/main/resources/prod.properties | awk -F= '{ print $2 }')

docker stop $containerName
docker rm $containerName
docker run \
       --user $(id -u):$(id -g) \
       --network qotd \
       --name $containerName \
       -d \
       -p $port:$port \
       -v ~/.blazartech:/home/$(whoami)/.blazartech \
       --health-cmd="curl --silent --fail http://localhost:$port/monitoring/health || exit 1" \
       --health-interval=60s \
       $imageName:$imageVersion
