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
        sh 'sbt clean test assembly'
        junit 'target/test-reports/*.xml,target/test-reports/**/*.xml'
      }
    }
    stage('Report') {
      steps {
        archiveArtifacts 'target/scala-2.12/*.jar'
      }
    }
  }
}
