pipeline {
    agent any
    stages {
        stage ('Backend Build') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }

        stage ('Backend  Unit tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage ('Backend Sonar Analysis') {

            environment {
                def scannerHome = tool 'SONAR_SCANNER'
            }

            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=5c76f9250e9cd93dba72533bc24cd50843975134 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
            }
        }

        stage ('Backend Quality Gate') {
            steps {
                sleep(30)
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: false, credentialsId: 'SonarToken'
                }
            }
        }

        stage ('Backend Deploy') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', onFailure: false, war: 'target/tasks-backend.war'
            }
        }

        stage ('API Unit test') {
            steps {
                dir('api-test') {
                    git credentialsId: 'GIthub_login', url: 'https://github.com/SouMarco/tasks-api-test'
                    bat 'mvn clean test'
                }
            }
        }
        
        stage ('Frontend Build') {
            steps {
                dir('tasks-frontend') {
                    git credentialsId: 'GIthub_login', url: 'https://github.com/SouMarco/tasks-frontend'
                    bat 'mvn clean package'
                }
            }
        }

        stage ('Frontend Deploy') {
            steps {
                dir('tasks-frontend') {
                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', onFailure: false, war: 'target/tasks.war'
                }
            }
        }
    }
}


