#!/bin/sh
## If you are running this in the gitlab CI, the "missing" env variables need to be declared as secrets. When using this from the command line, source the _secrets.sh bash script (only a sample is provided).


VERSION=$(cat ./VERSION)

## Building docker image
echo Building and pushing server version $VERSION to https://hub.docker.com.
docker build --tag $DOCKER_REPO/$DOCKER_IMAGE:latest --tag $DOCKER_REPO/$DOCKER_IMAGE:$VERSION --file ./docker/dockerfile-subluminal.yml --build-arg VERSION=$(echo $VERSION) --build-arg PORT=1729 . \
&& docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD \
&& docker push $DOCKER_REPO/$DOCKER_IMAGE
