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
                milestone label: 'Deploy to Staging'
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
        properties([buildDiscarder(logRotator(numToKeepStr: '10'))])
    }
    post {
        always {
            deleteDir()
        }
        success {
            echo 'Build successful'
        }
        failure {
            echo 'Build failed'
        }
        unstable {
            echo 'Build unstable'
        }
/*
        changed {
            //
        }
*/
    }
}
