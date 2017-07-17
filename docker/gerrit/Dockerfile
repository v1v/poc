FROM gerritforge/gerrit-ubuntu15.04:2.13.3

COPY ReviewDB.h2.db /var/gerrit/db/ReviewDB.h2.db
COPY repos /tmp/repos-wc
USER root
RUN chown -R gerrit:gerrit /tmp/repos-wc /var/gerrit/db/ReviewDB.h2.db

USER gerrit
RUN git config --global user.email "jenkins-pict@example.com" && \
    git config --global user.name "Jenkins PICT"

# Create repo working copies & then do bare clones to expose for work
RUN cd /tmp/repos-wc && mkdir /tmp/repos && \
    for r in midgard_sw ddk-gerrit; do ( \
        cd $r; git init && git add . && git commit -am "Initial commit"; \
        git clone --bare /tmp/repos-wc/$r /var/gerrit/git/$r.git; \
    ); done;

## RUN is part of the build phase and hostname is part of the run phase thought!
## https://stackoverflow.com/questions/36738381/pass-a-variable-to-a-dockerfile-from-a-docker-compose-yml-file
ARG GERRIT_FRONT_END_URL
RUN git config -f /var/gerrit/etc/gerrit.config gerrit.canonicalWebUrl ${GERRIT_FRONT_END_URL}
RUN git config -f /var/gerrit/etc/gerrit.config sendemail.smtpserver mail
RUN git config -f /var/gerrit/etc/gerrit.config plugins.allowRemoteAdmin true
CMD /var/gerrit/bin/gerrit.sh start && tail -f /var/gerrit/logs/error_log

# Docker doesn't set this to user home by default
ENV HOME /var/gerrit
