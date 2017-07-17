pipelineJob('pipeline') {
  concurrentBuild(false)
  triggers {
    gerritTrigger {
      gerritProjects {
        gerritProject {
          compareType('PLAIN')
          pattern('midgard_sw')
          branches {
            branch {
              compareType('REG_EXP')
              pattern('.*')
            }
          }
          disableStrictForbiddenFileVerification(false)
        }
      }
      serverName('gerrit-prod')
      triggerOnEvents {
        patchsetCreated {
          excludeDrafts(false)
          excludeTrivialRebase(false)
          excludeNoCodeChange(false)
        }
      }
      gerritBuildSuccessfulCodeReviewValue(1)
    }
  }
  definition {
    cps {
      sandbox()
      script(readFileFromWorkspace('resources/pipeline.groovy'))
    }
  }
}
