job('gatekeeper') {
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
    shell ('echo TI2')
    shell ('echo "Retrieve TI2 status"')
  }
}
