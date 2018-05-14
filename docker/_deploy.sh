TESTING_PORT=1727
STAGING_PORT=1728
PRODUCTION_PORT=1729

case $CI_JOB_NAME in
  testing)
    PORT=$TESTING_PORT
    ;;
  staging)
    PORT=$STAGING_PORT
    ;;
  production)
    PORT=$PRODUCTION_PORT
    ;;
  *)
    echo Could not find stage for deployment: $CI_JOB_NAME
esac

VERSION=$(cat ../VERSION)
echo Building and deploying server verison $VERSION to $CI_JOB_NAME environment.
docker build -t $DOCKER_REPO/$DOCKER_IMAGE:latest -t $DOCKER_REPO/$DOCKER_IMAGE:$VERSION ./docker/dockerfile-subluminal.yml
docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD
docker push $DOCKER_REPO/$DOCKER_IMAGE
apk update
apk add --no-cache openssh-client sshpass
sshpass -p $DEPLOY_PASSWORD ssh -o StrictHostKeyChecking=no $SSH_USER@$DEPLOYHOST docker stop subluminal_$CI_JOB_NAME || true
sshpass -p $DEPLOY_PASSWORD ssh $SSH_USER@$DEPLOYHOST docker pull $DOCKER_REPO/$DOCKER_IMAGE:latest
sshpass -p $DEPLOY_PASSWORD ssh $SSH_USER@$DEPLOYHOST docker run --rm -d -p $PORT:1729/tcp --name subluminal_$CI_JOB_NAME $DOCKER_REPO/$DOCKER_IMAGE:latest

## Better version from gitlab-ci.yml
- docker build -t subluminalthegame/subluminal:latest -t subluminalthegame/subluminal:$(echo $VERSION) --file ./docker/dockerfile-subluminal.yml --build-arg VERSION=$(echo $VERSION) --build-arg PORT=1729 .
- docker login -u subluminalthegame -p $DOCKERHUB_PASSWORD
- docker push subluminalthegame/subluminal
- apk update
- apk add --no-cache openssh-client sshpass
- sshpass -p $DEPLOY_PASSWORD ssh -o StrictHostKeyChecking=no gitlab@$DEPLOYHOST docker stop subluminal_staging || true
- sshpass -p $DEPLOY_PASSWORD ssh gitlab@$DEPLOYHOST docker pull subluminalthegame/subluminal:$VERSION
- sshpass -p $DEPLOY_PASSWORD ssh gitlab@$DEPLOYHOST docker run --rm -d -p 1727:1729/tcp --label subluminal --name subluminal_$CI_JOB_NAME subluminalthegame/subluminal:$VERSION