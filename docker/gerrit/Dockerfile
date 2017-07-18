FROM gerritforge/gerrit-ubuntu15.04:2.13.3

COPY ReviewDB.h2.db /var/gerrit/db/ReviewDB.h2.db
USER root
RUN chown -R gerrit:gerrit /var/gerrit/db/ReviewDB.h2.db

USER gerrit
RUN git config --global user.email "jenkins-pict@example.com" && \
    git config --global user.name "Jenkins PICT"

## RUN is part of the build phase and hostname is part of the run phase thought!
## https://stackoverflow.com/questions/36738381/pass-a-variable-to-a-dockerfile-from-a-docker-compose-yml-file
ARG GERRIT_FRONT_END_URL
RUN git config -f /var/gerrit/etc/gerrit.config gerrit.canonicalWebUrl ${GERRIT_FRONT_END_URL}
RUN git config -f /var/gerrit/etc/gerrit.config sendemail.smtpserver mail
RUN git config -f /var/gerrit/etc/gerrit.config plugins.allowRemoteAdmin true
CMD /var/gerrit/bin/gerrit.sh start && tail -f /var/gerrit/logs/error_log

# Docker doesn't set this to user home by default
ENV HOME /var/gerrit
