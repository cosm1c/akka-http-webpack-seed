pipeline {
  agent any
  stages {
    stage('Setup') {
      steps {
        sh 'echo Running ${BUILD_ID} ${GIT_COMMIT} on ${JENKINS_URL} at ${WORKSPACE}'
      }
    }
    stage('Build') {
      steps {
        sh 'sbt -no-colors clean test assembly'
      }
    }
    stage('Report') {
      steps {
        archiveArtifacts 'target/scala-2.12/*.jar'
        junit 'target/test-reports/*.xml'
      }
    }
  }
}