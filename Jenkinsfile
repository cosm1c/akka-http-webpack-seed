pipeline {
  agent {
    docker {
      image 'hseeberger/scala-sbt'
      args '-v $HOME/.ivy2:/root/.ivy2'
    }
    
  }
  stages {
    stage('Setup') {
      steps {
        sh '''sbt clean
'''
      }
    }
    stage('Build') {
      steps {
        sh 'sbt assembly'
      }
    }
    stage('Report') {
      steps {
        junit 'target/test-reports/**/*.xml'
        archiveArtifacts 'target/scala-2.12/*.jar'
      }
    }
  }
}