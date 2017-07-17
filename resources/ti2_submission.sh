#!/bin/bash -xe
## Given a particular Submission JSON files let's run in TI2.

echo "run something in TI2"
/bin/cat <<EOM >submission.json
{
    "authorizer": "vicmar02",
    "cancelTime": null,
    "cancelledBy": "vicmar02",
    "cancelledReason": "",
    "customParameters": {},
    "draftSubmission": false,
    "dryRun": true,
    "filterString": "",
    "includeDisabled": false,
    "jobBuilderCompleted": null,
    "jobBuilderStarted": null,
    "jobScheduler": "http://bc-d5-2-2.eu.iaas.arm.com/submission/api/v0",
    "jobSchedulerId": 1,
    "justification": "",
    "log": "Auto-upate: skipped because test plan 8r64 was up to date\n",
    "metadata": "Launched from Jenkins",
    "partialSubmission": false,
    "priority": 75,
    "projectId": 15,
    "repoBranchName": "refs/heads/trunk",
    "repoRevision": "bd8875a6a902009073fee02066f496b900d60014",
    "repoType": "GIT",
    "repoUrl": "ssh://mpd-gerrit.cambridge.arm.com:29418/midgard_sw/driver",
    "repos": {
        "gpuddk": {
            "branchName": "refs/heads/trunk",
            "revision": "bd8875a6a902009073fee02066f496b900d60014",
            "type": "GIT",
            "uri": "ssh://mpd-gerrit.cambridge.arm.com:29418/midgard_sw/driver"
        }
    },
    "result": "",
    "runName": "Test",
    "scenariosDisabled": [],
    "scenariosFailed": [],
    "scenariosFiltered": [],
    "scenariosIncluded": [],
    "schedulerCompleted": null,
    "schedulerStarted": null,
    "skipAutoUpdate": false,
    "state": 0,
    "submissionType": 2,
    "submitted": "2017-07-14T09:33:23.234689Z",
    "submitter": "vicmar02",
    "testPlanId": "8r64",
    "ti2_runs": [],
    "usedBy": [],
    "watchers": ""
}
EOM
# curl -s -u ${USER}:${PASSWORD} -X POST -H "Content-Type: application/json" http://mpgswes.euhpc.arm.com/api/v0/submissions/ -d @submission.json | python -m json.tool | tee submission.out
# curl -s -X POST -H "Authorization: Token $TOKEN" -H "Content-Type: application/json" http://mpgswes.euhpc.arm.com/api/v0/submissions/ -d @submission.json | python -m json.tool | tee submission.out
cat submission.out | python -c 'import sys, json; print json.load(sys.stdin)["id"]' > submission.id