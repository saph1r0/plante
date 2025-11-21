pipeline {
    agent any

    tools {
        maven 'MAVEN'
        jdk 'JAVA'
    }

    triggers {
        githubPush()
    }

    environment {
        SONARQUBE_ENV = credentials('sonarqube-local') // si usas "Secret Text"
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

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube-local') {
                    bat """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=Plantapp \
                        -Dsonar.projectName=Plantapp \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=%SONARQUBE_ENV%
                    """
                }
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
            echo "Build completado con éxito "
        }
        failure {
            echo "El build falló "
        }
    }
}
