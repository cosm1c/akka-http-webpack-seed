pipeline {
  agent any
  stages {
    stage('Setup') {
      steps {
        sh 'echo START BUILD_ID=${BUILD_ID} GIT_COMMIT=${GIT_COMMIT} JENKINS_URL=${JENKINS_URL} WORKSPACE=${WORKSPACE}'
      }
    }
    stage('Build') {
      steps {
        parallel(
          "Backend Unit Tests": {
            sh 'sbt -no-colors clean test'
            junit 'target/test-reports/*.xml'
            
          },
          "Frontend Unit Tests": {
            sh 'npm run ci-test'
            junit 'target/ui/test-reports/*.xml'
            
          }
        )
      }
    }
    stage('User Acceptance') {
      steps {
        input 'Does the staging environment look ok?'
      }
    }
    stage('Report') {
      steps {
        archiveArtifacts 'target/scala-2.12/*.jar'
      }
    }
  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '10'))
  }
}