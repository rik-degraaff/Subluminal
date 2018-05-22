#!/bin/sh
## Stops the environment started form gitlab.


CI_COMMIT_SHA=${CI_COMMIT_SHA:0:7}

echo "Stopping environment version $TAG with ID $CI_COMMIT_SHA."
sshpass -p $DEPLOY_PASSWORD ssh -o StrictHostKeyChecking=no $SSH_USER@$DEPLOYHOST docker stop subluminal_$CI_COMMIT_SHA
sshpass -p $DEPLOY_PASSWORD ssh -o StrictHostKeyChecking=no $SSH_USER@$DEPLOYHOST docker rm -v subluminal_$CI_COMMIT_SHA