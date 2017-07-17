#!/bin/bash
USERNAME="`git config user.name`"
EMAIL="`git config user.email`"
USERNAME="$USER"
HTTP_PASS=goober
FULLNAME="`git config user.name`"

source ./env.config
# Fixes issues with repo url getting wrong username, since it uses local part of
# email for SSH username
git config --global review.http://${PROXY_HOST}:8080/.username $USER
chmod 400 jenkins/jenkins-pict_rsa
cat ~/.ssh/id_rsa.pub | ssh -i jenkins/jenkins-pict_rsa -p 29418 admin@${PROXY_HOST} \
      gerrit create-account --group \"Non-Interactive Users\" \
      --full-name \"$FULLNAME\" --email \"$EMAIL\" --http-password \"$HTTP_PASS\" --ssh-key - $USERNAME