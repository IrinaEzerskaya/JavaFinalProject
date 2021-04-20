pipeline {
    agent any
    stages {
        stage('Build application') {
            steps {
                echo 'Building..'
                git 'https://github.com/IrinaEzerskaya/JavaFinalProject.git'
            }
        }
        stage('Functional Tests') {
            steps {
                echo 'Testing..'
                sh 'mvn clean test'
            }
        }
    }
    post {
        always {
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
        }
    }
}