pipeline {
  agent {
    docker {
      args '-v /root/.m2:/root/.m2 -u root'
      image 'gizmotronic/oracle-java8'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'apt-get update; apt-get install -y maven'
        sh 'mvn clean verify'
      }
    }
  }
}