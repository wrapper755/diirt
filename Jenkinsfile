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
        sh 'apt install maven'
        sh 'mvn clean verify'
      }
    }
  }
}