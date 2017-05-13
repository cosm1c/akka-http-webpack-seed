#!groovy
/*
 * Jenkins Blue Ocean Pipeline POC.
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
                    echo "TODO: re-enable backend tests"
/*
                    ansiColor('xterm') {
                        timeout(10) {
                            sh 'sbt clean test'
                        }
                    }
                    junit 'target/test-reports.xml'
*/
                }, "Frontend Unit Tests": {
                    echo "TODO: re-enable frontend tests"
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
                milestone label: 'Staged for User acceptance.', ordinal: 1
                lock(resource: 'Staging environment', inversePrecedence: true) {
                    echo 'TODO: deploy somewhere'
                    echo 'TODO: integrationt tests'
                }
            }
        }
        stage('User Acceptance') {
            steps {
                milestone label: 'User acceptance passed.', ordinal: 2
                input 'Does the staging environment look ok?'
            }
        }
        stage('Complete') {
            steps {
                echo 'Final step'
            }
        }
    }
    post {
/*
        always {
            //
        }
*/
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
