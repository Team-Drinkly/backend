pipeline {
    agent any
    tools {
        jdk 'jdk21' // Jenkins에서 설정한 JDK 이름
    }
    environment {
        ECR_URL = '481665105550.dkr.ecr.ap-northeast-2.amazonaws.com'  // AWS ECR URL
        ECR_REPOSITORY = 'drinkhere/spring-server'  // ECR 리포지토리 이름
        AWS_CREDENTIAL_NAME = 'awsCredentials'
        REGION = 'ap-northeast-2'
    }
    parameters {
        string(name: 'db_url', defaultValue: '', description: 'Database URL')
        string(name: 'db_username', defaultValue: '', description: 'Database Username')
        string(name: 'db_password', defaultValue: '', description: 'Database Password')
    }

    stages {
        stage('Check Java Version') {
            steps {
                sh 'java -version'  // Java 버전 확인
            }
        }
        stage('Prepare Secret Variable') {
            steps {
                script {
                    // 환경 변수로 설정하고 템플릿 파일에 변수 대입
                    sh """
                        export DB_URL=${params.db_url}
                        export DB_USERNAME=${params.db_username}
                        export DB_PASSWORD=${params.db_password}
                        envsubst < domain/domain-rds/src/main/resources/application-rds-prod.yml.template > domain/domain-rds/src/main/resources/application-rds-prod.yml
                    """
                }
            }
        }
        stage('ECR Login') {
            steps {
                script {
                    // ECR 로그인
                    withCredentials([aws(credentialsId: "${AWS_CREDENTIAL_NAME}", region: "${REGION}")]) {
                        sh "aws ecr get-login-password --region ${REGION} | docker login --username AWS --password-stdin ${ECR_URL}"
                    }
                }
            }
        }
        stage('Build & Docker Image') {
            steps {
                sh './gradlew clean :execute:bootJar' // Gradle 빌드
            }
        }
        stage('Push Docker Image to ECR') {
            steps {
                script {
                    // 현재 날짜를 기반으로 IMAGE_NAME 생성 (yyyyMMdd-HHmmss 형식)
                    def imageName = sh(script: 'date +%Y%m%d-%H%M%S', returnStdout: true).trim()

                    docker.withRegistry("https://${ECR_URL}", "ecr:${REGION}:${AWS_CREDENTIAL_NAME}") {
                        // Docker 이미지 빌드
                        def app = docker.build("${ECR_URL}/${ECR_REPOSITORY}:${imageName}")
                        // ECR에 Docker 이미지 푸시
                        app.push("${imageName}")
                    }
                    // 푸시 후 로컬에서 Docker 이미지 삭제
                    sh "docker rmi ${ECR_URL}/${ECR_REPOSITORY}:${imageName}"
                }
            }
        }
    }
}
