pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Clean Workspace') {
                    steps {
                        cleanWs()
                    }
                }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test -U'
            }
        }
    }
}