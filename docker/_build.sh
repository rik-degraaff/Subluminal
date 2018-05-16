#!/bin/sh
## If you are running this in the gitlab CI, the "missing" env variables need to be declared as secrets. When using this from the command line, source the _secrets.sh bash script (only a sample is provided).


if [[ $(cat /proc/version) != *"Alpine"* ]];then
  # Get secret variables for deployment into local shell
  echo "Not in gitlab ci. Sourcing secret variables."
  source ./docker/_secrets.sh
fi

TAG=$(cat ./VERSION)
echo $CI_COMMIT_REF_NAME
echo $CI_COMMIT_REF_SLUG
echo $CI_COMMIT_SHA

## Building docker image
echo "Building and pushing server version $TAG to https://hub.docker.com."
docker build -q --tag $DOCKER_REPO/$DOCKER_IMAGE:latest --tag $DOCKER_REPO/$DOCKER_IMAGE:$TAG --file ./docker/dockerfile-subluminal.yml --build-arg VERSION=$(echo $TAG) --build-arg PORT=1729 . && \
docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD && \
docker push $DOCKER_REPO/$DOCKER_IMAGE
