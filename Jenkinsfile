pipeline {
    agent any
    stages {
        stage ('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }

        stage ('Unit tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage ('Sonar Analysis') {

            environment {
                def scannerHome = tool 'SONAR_SCANNER'
            }

            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=5c76f9250e9cd93dba72533bc24cd50843975134 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
            }
        }

        stage ('Quality Gate') {
            steps {
                sleep(30)
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: false, credentialsId: 'SonarToken'
                }
            }
        }
    }
}

