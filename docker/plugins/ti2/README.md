# TI2 plugin

Build TI2 gerrit plugin in Docker based on:
- https://hub.docker.com/_/maven/
- https://confluence.arm.com/display/DP/Plug-ins#Plug-ins-TI2/TSSbutton


## Actions
- sh -xe run.sh
- ssh mpd-gerrit -p 29418 gerrit plugin install -n ti2.jar /location/on/gerrit/ti2-plugin-2.13.jar  # Install

## If you are running gerrit locally then
- docker cp ti2/build/ti2-plugin-2.13.jar gerrit:/tmp
- ssh -i jenkins/jenkins-pict_rsa -p 29418 jenkins-pict@localhost gerrit plugin add /tmp/ti2-plugin-2.13.jar
- ssh -i jenkins/jenkins-pict_rsa -p 29418 jenkins-pict@localhost gerrit plugin ls