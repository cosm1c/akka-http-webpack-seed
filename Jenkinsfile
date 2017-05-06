pipeline {
  agent {
    docker {
      image 'tarunbhm/docker-alpine-oraclejdk8'
      args '-v ~/.ivy2:/root/.ivy2'
    }
    
  }
  stages {
    stage('Setup') {
      steps {
        sh '''echo PATH=${PATH}

sbt clean
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