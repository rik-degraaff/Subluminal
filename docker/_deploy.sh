#!/bin/bash
## If you are running this in the gitlab CI, the "missing" env variables need to be declared as secrets. When using this from the command line, source the _secrets.sh bash script (only a sample is provided).


VERSION=$(cat ./VERSION)
CI_JOB_NAME=manual

## Deploying docker image
#apk update
#apk add --no-cache openssh-client sshpass
sshpass -p $DEPLOY_PASSWORD ssh -o StrictHostKeyChecking=no $DEPLOY_USERNAME@$DEPLOY_HOST docker stop subluminal_$CI_JOB_NAME || true && \
sshpass -p $DEPLOY_PASSWORD ssh $DEPLOY_USERNAME@$DEPLOY_HOST docker pull $DOCKER_REPO/$DOCKER_IMAGE:latest && \
sshpass -p $DEPLOY_PASSWORD ssh $DEPLOY_USERNAME@$DEPLOY_HOST docker run --rm -d -p $PORT:1729/tcp --name subluminal_$CI_JOB_NAME $DOCKER_REPO/$DOCKER_IMAGE:$VERSION