#!groovy
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    stages {
        stage('Setup') {
            steps {
                sh 'echo START BUILD_ID=${BUILD_ID} WORKSPACE=${WORKSPACE}'
                // TODO: other setup
            }
        }
        stage('Build') {
            steps {
                parallel "Backend Unit Tests": {
                    ansiColor('xterm') {
                        timeout(10) {
                            sh 'sbt clean test'
                        }
                    }
                    junit 'target/test-reports/*.xml'

                }, "Frontend Unit Tests": {
                    ansiColor('xterm') {
                        timeout(10) {
                            sh 'yarn install --no-lockfile'
                            sh 'npm run ci-test'
                        }
                    }
                    junit 'target/ui/test-reports/*.xml'
                }
            }
        }
        stage('Staging') {
            steps {
                milestone label: 'Deploy to Staging', ordinal: 1
                echo 'TODO: deploy to docker container'
            }
        }
        stage('User Acceptance') {
            steps {
                milestone label: 'User acceptance', ordinal: 1
                input 'Does the staging environment look ok?'
            }
        }
    }
    post {
        always {
            deleteDir()
        }
        success {
            echo 'Build successful.'
        }
        failure {
            echo 'Build failed.'
        }
        unstable {
            echo 'Build unstable.'
        }
/*
        changed {
            //
        }
*/
    }
}
