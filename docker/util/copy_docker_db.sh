#!/bin/bash

# Assuming you're running docker natively, copy the review db to local copy
docker cp gerrit:/var/gerrit/db/ReviewDB.h2.db gerrit/ReviewDB.h2.db
chown "$USER" gerrit/ReviewDB.h2.db