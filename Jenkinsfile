pipeline {
  agent {
    docker {
      args '-v /root/.m2:/root/.m2 -u root'
      image 'sdorra/oracle-java-8'
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