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
                sh "mvn -B -DskipTests clean package"
            }
        }

        stage('Test') {
            steps {
                sh "mvn test"
            }
        }

        stage('Package Jar') {
            steps {
                sh "mvn package"
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
