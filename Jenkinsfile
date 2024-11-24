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
    stages {
        stage('Check Java Version') {
            steps {
                sh 'java -version'  // Java 버전 확인
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean :execute:bootJar' // Gradle 빌드
            }
        }
        stage('Push image') {
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
