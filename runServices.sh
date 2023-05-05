#! /bin/sh

appEnv=${ENVIRONMENT:-test}

artifact=$(getPomAttribute.sh artifactId)
version=$(getPomAttribute.sh version)

java -jar target/$artifact-$version.jar --spring.config.name=application,$appEnv
