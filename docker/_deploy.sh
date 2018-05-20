#!/bin/bash
## If you are running this in the gitlab CI, the "missing" env variables need to be declared as secrets. When using this from the command line, source the _secrets.sh bash script (only a sample is provided).


TAG=$(cat ./VERSION)

if [[ $(cat /proc/version) = *"Alpine"* ]];then
  # Install dependencies in alpine linux
  echo "Alpine linux found."
  apk update
  apk add --no-cache openssh-client sshpass bash
  #bash
  export CI_COMMIT_SHA=${CI_COMMIT_SHA:0:7}

else
  # Get secret variables for deployment into local shell
  echo "Other *nix found."
  source ./docker/_secrets.sh
  SHA=$(git rev-parse HEAD)
  export CI_COMMIT_SHA=${SHA:0:7}
fi

## Deploying docker image
echo "Deploying server version $TAG with ID $CI_COMMIT_SHA to $CI_COMMIT_SHA.s01.subluminal.tech"
sshpass -p $DEPLOY_PASSWORD ssh -o StrictHostKeyChecking=no $DEPLOY_USERNAME@$DEPLOY_HOST docker stop subluminal_$CI_COMMIT_SHA || true && \
sshpass -p $DEPLOY_PASSWORD ssh -o StrictHostKeyChecking=no $DEPLOY_USERNAME@$DEPLOY_HOST docker rm -v subluminal_$CI_COMMIT_SHA || true && \
sshpass -p $DEPLOY_PASSWORD ssh -o StrictHostKeyChecking=no $DEPLOY_USERNAME@$DEPLOY_HOST docker pull $DOCKER_REPO/$DOCKER_IMAGE:$TAG && \
sshpass -p $DEPLOY_PASSWORD ssh $DEPLOY_USERNAME@$DEPLOY_HOST docker run -d -p 1729/tcp --restart=always -e VIRTUAL_HOST="$CI_COMMIT_SHA.s01.subluminal.tech" --name subluminal_$CI_COMMIT_SHA $DOCKER_REPO/$DOCKER_IMAGE:$TAG
