pipeline {
  agent {
    docker {
      args '-v /root/.m2:/root/.m2 -u root'
      image 'andreptb:8-alpine'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean verify'
      }
    }
  }
}