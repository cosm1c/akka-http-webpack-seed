#!groovy
pipeline {
    agent any
    options {
        // Only keep the 10 most recent builds
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    stages {
        stage('Unit Tests') {
            steps {
                sh 'echo BUILD_ID=${BUILD_ID} GIT_COMMIT=${GIT_COMMIT} JENKINS_URL=${JENKINS_URL} WORKSPACE=${WORKSPACE}'
                sh 'npm run ci-test'
                junit 'target/ui/test-reports/*.xml'
            }
        }
        stage('Build') {
            steps {
                sh 'sbt -no-colors clean test assembly'
                junit 'target/test-reports/*.xml'
            }
        }
        stage('User Acceptance') {
            steps {
                input "Does the staging environment look ok?"
                // TODO: select: Yes/No/Rollback
                // TODO: if successful => record user-accepted-date
            }
        }
        stage('Report') {
            steps {
                archiveArtifacts 'target/scala-2.12/*.jar'
            }
        }
    }
}
