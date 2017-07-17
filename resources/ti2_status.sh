#!/bin/bash -xe
## Given a particular SUBMISSION let's see whether its run has already finished.
##  There are two different entry points:
##    - Kaizen
##    - TI
##  Unfortunately, TI2 entry point is not a standard RestAPI:
##
##    If RUN has not been logged yet then 404 http code
##    Otherwise, its status is based on the below :
##      - red
##      - green
##      - not_run
##      - amber
##		  - None

#SUBMISSION=$(cat submission.id)
SUBMISSION=47783
SUBMISSION_ENTRYPOINT="http://mpgswes.euhpc.arm.com/api/v0/submissions"
RUN_ENTRYPOINT="http://mpdti2.euhpc.arm.com/noauth/get_rating/run"

# Retrieve the TI2 RUN_ID given a particular Kaizen Submission
RUN_ID=$(curl --silent ${SUBMISSION_ENTRYPOINT}/${SUBMISSION}/?format=json | python -c 'import sys, json; print json.load(sys.stdin)["ti2_runs"][0]')

RETRY=1
RESULT=1
SLEEP=60
until [ ${RETRY} -eq 0 ]; do
  # Retrieve the RUN_ID HTTP Status given a particular RUN_ID
  STATUS=$(curl ${RUN_ENTRYPOINT}/$(basename ${RUN_ID}) \
       --write-out %{http_code} \
       --silent \
       --output /dev/null)

  if [ $STATUS = "200" ] ; then
    echo 'RUN has finished already'
    STATUS=$(curl --silent ${RUN_ENTRYPOINT}/$(basename ${RUN_ID}))

    case $STATUS in
      green*)
        RESULT=0
        RETRY=0
        SLEEP=0
        ;;
      red*)
        RESULT=1
        RETRY=0
        ;;
      not_run)
        RESULT=1
        RETRY=1
        ;;
      amber)
        RESULT=0
        RETRY=0
        SLEEP=0
        ;;
      *)
        RESULT=1
        RETRY=1
        ;;
      esac
  else
    echo "Let's rerun this again"
  fi
  sleep ${SLEEP}
done

exit ${RESULT}