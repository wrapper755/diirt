pipeline {
  agent {
    docker {
      args '-v /root/.m2:/root/.m2 -u root'
      image 'gizmotronic/oracle-java'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'apt-get install maven'
        sh 'mvn clean verify'
      }
    }
  }
}