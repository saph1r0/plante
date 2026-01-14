pipeline {
    agent any

    tools {
        maven 'MAVEN'
        jdk 'JAVA'
    }

    environment {
        SONARQUBE_TOKEN = credentials('sonarqube-local')
        CI = 'true'
    }

    triggers {
        githubPush()
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
                sh 'mvn clean package -DskipTests=true'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube-local') {
                    sh '''
                    mvn sonar:sonar \
                    -Dsonar.projectKey=Plantapp \
                    -Dsonar.projectName=Plantapp \
                    -Dsonar.host.url=http://localhost:9000 \
                    -Dsonar.token=${SONARQUBE_TOKEN}
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: false
                }
            }
        }

        stage('Build User Service') {
            steps {
                dir('user-service') {
                    sh 'mvn clean package -DskipTests=true'
                }
            }
        }

        stage('Start Services') {
            steps {
                sh '''
                # Start Plante
                nohup java -jar target/*.jar > plante.log 2>&1 &
                echo $! > plante.pid

                # Start User Service
                export DB_URL=jdbc:mysql://localhost:3306/usersdb
                export DB_USERNAME=root
                export DB_PASSWORD=
                nohup java -jar user-service/target/*.jar > user-service.log 2>&1 &
                echo $! > user-service.pid

                # Wait for services to start
                sleep 60
                '''
            }
        }

        stage('Health Check') {
            steps {
                script {
                    retry(10) {
                        sleep 5
                        sh 'curl -f http://localhost:8080/actuator/health'
                    }
                }
            }
        }

        stage('Functional Tests') {
            steps {
                dir('plantapp-functional-tests') {
                    sh 'mvn clean test -Dtest=**/*FunctionalTest'
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'plantapp-functional-tests/target/surefire-reports',
                        reportFiles: '*.html',
                        reportName: 'Functional Test Report'
                    ])
                }
            }
        }

        stage('OWASP ZAP - Plante') {
            steps {
                sh '''
                docker run --rm --network="host" \
                  -v $(pwd):/zap/wrk/:rw \
                  ghcr.io/zaproxy/zaproxy:stable \
                  zap-baseline.py \
                  -t http://localhost:8080/web/login \
                  -r zap-plante.html
                '''
            }
        }

        stage('OWASP ZAP - User Service') {
            steps {
                sh '''
                docker run --rm --network="host" \
                  -v $(pwd):/zap/wrk/:rw \
                  ghcr.io/zaproxy/zaproxy:stable \
                  zap-baseline.py \
                  -t http://localhost:8082/api/auth/login \
                  -r zap-user-service.html
                '''
            }
        }
    }

    post {
        always {
            publishHTML([
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: '.',
                reportFiles: 'zap-*.html',
                reportName: 'OWASP ZAP Security Report'
            ])

            sh '''
            # Kill services
            if [ -f plante.pid ]; then
                kill $(cat plante.pid) || true
                rm plante.pid
            fi
            if [ -f user-service.pid ]; then
                kill $(cat user-service.pid) || true
                rm user-service.pid
            fi

            # Alternative: kill by port
            lsof -ti:8080 | xargs kill -9 || true
            lsof -ti:8082 | xargs kill -9 || true
            '''
        }

        success {
            echo "Pipeline CI/CD ejecutado correctamente"
            emailext (
                subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "El pipeline completó exitosamente. Ver detalles: ${env.BUILD_URL}",
                to: "hellen.juandedios@gmail.com"
            )
        }

        failure {
            echo "Pipeline falló"
            emailext (
                subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "El pipeline falló. Ver detalles: ${env.BUILD_URL}",
                to: "hellen.juandedios@gmail.com"
            )
        }
    }
}