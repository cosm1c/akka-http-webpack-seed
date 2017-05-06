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
                sh 'echo Running ${BUILD_ID} ${GIT_COMMIT} on ${JENKINS_URL} at ${WORKSPACE}'
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
