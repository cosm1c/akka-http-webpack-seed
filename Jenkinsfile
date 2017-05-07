#!groovy
pipeline {
  agent any
  stages {
    stage('Setup') {
      steps {
        sh 'echo START BUILD_ID=${BUILD_ID} WORKSPACE=${WORKSPACE}'
      }
    }
    stage('Build') {
      steps {
        parallel(
          "Backend Unit Tests": {
            wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
              sh 'sbt clean test'
            }
            junit 'target/test-reports/*.xml'
            
          },
          "Frontend Unit Tests": {
            wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
              sh 'yarn install --no-lockfile'
              sh 'npm run ci-test'
            }
            junit 'target/ui/test-reports/*.xml'
            
          }
        )
      }
    }
    stage('Staging') {
      steps {
        echo 'TODO: deploy to docker container'
      }
    }
    stage('User Acceptance') {
      steps {
        input 'Does the staging environment look ok?'
      }
    }
  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '10'))
  }
}