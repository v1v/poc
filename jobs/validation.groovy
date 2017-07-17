job('validation') {
  concurrentBuild(false)
  triggers {
    gerrit {
      configure {
        it << serverName('gerrit-prod')
      }
      events {
        patchsetCreated()
      }
      project('midgard_sw', 'master')
    }
  }
  steps {
    shell ('echo compile')
    shell ('echo test')
    shell(readFileFromWorkspace('resources/ti2_submission.sh'))
    shell(readFileFromWorkspace('resources/ti2_status.sh'))
  }
}
