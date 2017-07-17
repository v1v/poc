FROM jenkins:2.19.3

### Disable jenkins2 wizard
RUN echo 2.19.3-alpine > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state
RUN echo 2.19.3-alpine > /usr/share/jenkins/ref/jenkins.install.InstallUtil.lastExecVersion

### configure a list of plugins
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt

# In order to run some commands in the masterside
USER root
RUN apt-get install -y curl python

# Keys for gerrit user, passphrase is EMPTY
COPY jenkins-pict_rsa /tmp/
RUN chown jenkins:jenkins /tmp/jenkins-pict_rsa

USER jenkins

COPY jenkins_home /usr/share/jenkins/ref
