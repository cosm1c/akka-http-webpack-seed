#!groovy
/*
 * Jenkins Blue Ocean Pipeline
 */
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    stages {
        stage('Setup') {
            steps {
                sh 'echo START BUILD_ID=${BUILD_ID} WORKSPACE=${WORKSPACE}'
                checkout scm
            }
        }
        stage('Build') {
            steps {
                parallel "Backend Unit Tests": {
                    echo "TODO: run backend testst"
/*
                    ansiColor('xterm') {
                        timeout(10) {
                            sh 'sbt clean test'
                        }
                    }
                    junit 'target/test-reports.xml'
*/

                }, "Frontend Unit Tests": {
                    echo "TODO: run frontend testst"
/*
                    ansiColor('xterm') {
                        timeout(10) {
                            sh 'yarn install --no-lockfile'
                            sh 'npm run ci-test'
                        }
                    }
                    junit 'target/ui/test-reports.xml'
*/
                }
            }
        }
        stage('Staging') {
            steps {
                milestone label: 'Build successful', ordinal: 1
                lock(resource: 'Staging environment', inversePrecedence: true) {
                    echo 'TODO: deploy to docker container'
                    echo 'TODO: integrationt tests'
                }
            }
        }
        stage('User Acceptance') {
            steps {
                milestone 5
                input 'Does the staging environment look ok?'
                milestone 6
            }
        }
        stage('Complete') {
            steps {
                echo 'Final step'
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
