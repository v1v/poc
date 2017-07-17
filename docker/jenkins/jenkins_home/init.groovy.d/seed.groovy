/*** BEGIN META {
"name" : "seed.groovy",
"comment" : "This script creates/launches the seed job",
"parameters" : [ ],
"core": "2.19.3",
"authors" : [
{ name : "Victor Martinez" }
]
} END META**/

import jenkins.model.Jenkins
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.plugin.JenkinsJobManagement

def env = System.getenv()

def gerrit_front_end_url = env['GERRIT_FRONT_END_URL']

def jobDslScript = """
job('seed') {
  scm {
    git {
      remote {
        url('${gerrit_front_end_url}/ddk-gerrit')
        branch('master')
      }
    }
  }
  triggers {
    gerritTrigger {
      gerritProjects {
        gerritProject {
          compareType('PLAIN')
          pattern('ddk-gerrit')
          branches {
            branch {
              compareType('PLAIN')
              pattern('master')
            }
          }
          filePaths {
            filePath {
              compareType('REG_EXP')
              pattern('jobs/.*')
            }
          }
          disableStrictForbiddenFileVerification(false)
        }
      }
      serverName('gerrit-prod')
      triggerOnEvents {
        changeMerged()
      }
    }
  }
  steps {
    dsl {
      external('jobs/*.groovy')
      additionalClasspath('jobs/src/')
      removeAction('DELETE')
      removeViewAction('DELETE')
    }
  }
}
"""
def workspace = new File('.')
def jobManagement = new JenkinsJobManagement(System.out, [:], workspace)
new DslScriptLoader(jobManagement).runScript(jobDslScript)


def cause = new hudson.model.Cause.RemoteCause('UponStartUp', 'StartUp')
def causeAction = new hudson.model.CauseAction(cause)
Jenkins.instance?.queue.schedule(Jenkins.instance?.getItemByFullName('seed'), 0, causeAction)
