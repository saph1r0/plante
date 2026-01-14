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
        SONARQUBE_ENV = credentials('sonarqube-local')
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'sophia-erika-hellen-pruebas',
                    credentialsId: 'github-tokens',
                    url: 'https://github.com/saph1r0/plante.git'
            }
        }

        stage('Build Plante') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube-local') {
                    bat '''
                    mvn sonar:sonar ^
                    -Dsonar.projectKey=Plantapp ^
                    -Dsonar.projectName=Plantapp ^
                    -Dsonar.host.url=http://localhost:9000 ^
                    -Dsonar.login=%SONARQUBE_ENV%
                    '''
                }
            }
        }

        stage('Build User Service') {
            steps {
                dir('user-service') {
                    bat 'mvn clean package -DskipTests=true'
                }
            }
        }

        stage('Start Services') {
            steps {
                bat '''
                start cmd /c "java -jar target\\*.jar"
                start cmd /c ^
                "set DB_URL=jdbc:mysql://localhost:3306/usersdb&& ^
                 set DB_USERNAME=root&& ^
                 set DB_PASSWORD=&& ^
                 java -jar user-service\\target\\*.jar"
                timeout /t 40
                '''
            }
        }

        stage('OWASP ZAP - Plante') {
            steps {
                bat '''
                zap-cli start
                zap-cli open-url http://localhost:8080
                zap-cli active-scan http://localhost:8080
                zap-cli report -o zap-plante.html -f html
                '''
            }
        }

        stage('OWASP ZAP - User Service') {
            steps {
                bat '''
                zap-cli open-url http://localhost:8081
                zap-cli active-scan http://localhost:8081
                zap-cli report -o zap-user-service.html -f html
                zap-cli shutdown
                '''
            }
        }
    }

    post {
        success {
            echo "Pipeline CI/CD ejecutado correctamente"
        }
        failure {
            echo "Pipeline falló"
        }
    }
}
