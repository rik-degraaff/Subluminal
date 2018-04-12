echo Building and deploying server verison $VERSION to $CI_JOB_NAME environment.
sshpass -p $DEPLOY_PASSWORD ssh -o StrictHostKeyChecking=no $SSH_USER@$DEPLOYHOST docker stop subluminal_$CI_JOB_NAME