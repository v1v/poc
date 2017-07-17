pipeline {
  agent {
    label ""
  }
  stages {
    stage("Preparation") {
      steps {
          sleep 2
          echo "We're not doing anything particularly special here."
      }

      post {
        success {
          echo "Only when we haven't failed running the first stage"
        }

        failure {
          echo "Only when we fail running the first stage."
        }
      }
    }

    stage('Build') {
      steps {
        sleep 5
        echo "This time, will run some builds"
      }
    }

    stage('Testing') {
      steps {
        parallel(syntax: {
                  sleep 10
                  echo "Running some syntax validation"
                 },
                 unit: {
                   sleep 30
                   echo "Running some unit validation"
                 },
                 afs: {
                   sleep 10
                   echo "Running some afs validation"
                 })
      }
    }
    stage('Characterisation') {
      steps {
        parallel(performance: {
                  echo "Running performance"
                 },
                 conformance: {
                   sleep 15
                   echo "Running conformance"
                 })
      }
    }
    stage('Dumping') {
      steps {
        echo "Running some Dumping"
      }
    }
  }

  post {
    always {
      deleteDir()
    }
  }
}