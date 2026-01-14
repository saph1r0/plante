pipeline {
    agent any

    tools {
        maven 'MAVEN'
        jdk 'JAVA'
    }

    environment {
        SONARQUBE_TOKEN = credentials('sonarqube-local')
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
                    -Dsonar.login=%SONARQUBE_TOKEN%
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
                REM ===== START PLANTE =====
                start "" cmd /c "java -jar target\\*.jar"

                REM ===== START USER SERVICE =====
                start "" cmd /c "set DB_URL=jdbc:mysql://localhost:3306/usersdb&&set DB_USERNAME=root&&set DB_PASSWORD=&&java -jar user-service\\target\\*.jar"

                REM ===== WAIT SERVICES =====
                ping 127.0.0.1 -n 40 > nul
                '''
            }
        }

        stage('OWASP ZAP - Plante') {
            steps {
                bat '''
                cd /d "C:\\Program Files\\ZAP\\Zed Attack Proxy"

                zap.bat ^
                  -cmd ^
                  -quickurl http://localhost:8080/web/login ^
                  -quickout "C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\plante-ci-cd\\zap-plante.html"
                '''
            }
        }

        stage('OWASP ZAP - User Service') {
            steps {
                bat '''
                cd /d "C:\\Program Files\\ZAP\\Zed Attack Proxy"

                zap.bat ^
                  -cmd ^
                  -zapit http://localhost:8082/api/auth/login ^
                  -quickout "C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\plante-ci-cd\\zap-user-service.html"
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
