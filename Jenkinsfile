pipeline {
  agent any
  stages {
    stage('Setup') {
      steps {
        sh 'echo Running ${BUILD_ID} on ${JENKINS_URL} at ${WORKSPACE}'
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