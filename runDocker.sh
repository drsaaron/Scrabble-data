#! /bin/sh

# get the IP address of the host
ip=$(ifconfig | grep inet | awk '$1=="inet" {print $2}' | tail -1)

# run the service
imageName=$(dockerImageName.sh)
imageVersion=0.1 #$(getPomAttribute.sh version | sed 's/-RELEASE$//')
containerName=$(echo $imageName | sed -re 's%^.*/([a-zA-Z]*)$%\1%') # could also use basename $(dockerImageName.sh)

docker stop $containerName
docker rm $containerName
docker run --user $(id -u):$(id -g) --network qotd --name $containerName -d -p 4100:4100 -v ~/.blazartech:/home/$(whoami)/.blazartech $imageName:$imageVersion
