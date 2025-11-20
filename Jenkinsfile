pipeline {
    agent any

    tools {
        maven 'MAVEN'
        jdk 'JAVA'
    }

    triggers {
        githubPush()
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'sophia-erika-hellen-pruebas',
                    credentialsId: 'github-token',
                    url: 'https://github.com/saph1r0/plante.git'
            }
        }

        stage('Build') {
            steps {
                bat "mvn -B -DskipTests clean package"
            }
        }

        stage('Test') {
            steps {
                bat "mvn test"
            }
        }

        stage('Package Jar') {
            steps {
                bat "mvn package"
            }
        }
    }

    post {
        success {
            echo "Build completado con éxito 🌿"
        }
        failure {
            echo "El build falló ❌"
        }
    }
}
