#!/bin/bash
set -e

source ./env.config
mkdir -p gerrit/repos/ddk-gerrit
cp -rf ../../ddk-gerrit/jobs gerrit/repos/ddk-gerrit
cp -rf ../../ddk-gerrit/*.* gerrit/repos/ddk-gerrit
cp -rf ../../ddk-gerrit/gradle* gerrit/repos/ddk-gerrit
cp -rf ../../ddk-gerrit/resources gerrit/repos/ddk-gerrit
cp -rf ../../ddk-gerrit/src gerrit/repos/ddk-gerrit
docker-compose ${COMPOSE_OPT} $@
