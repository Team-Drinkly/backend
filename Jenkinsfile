pipeline {
    agent any
    tools {
        jdk 'jdk21'
    }
    environment {
        JAVA_HOME = '/var/lib/jenkins/tools/hudson.model.JDK/jdk21/amazon-corretto-21.0.3.9.1-linux-x64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        ECR_URL = '481665105550.dkr.ecr.ap-northeast-2.amazonaws.com'  // ECR URL
        ECR_REPOSITORY = 'drinkhere/spring-server'  // ECR 리포지토리 이름
    }
    stages {
        stage('Check Java Version') {
            steps {
                sh 'java -version'  // Java 버전 확인
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean :execute:bootJar'
            }
        }
        stage('Push image') { // Push image stage가 stages 내에 포함됨
            steps {
                script {
                    // 현재 날짜를 기반으로 IMAGE_NAME 생성 (yyyyMMdd-HHmmss 형식)
                    def imageName = sh(script: 'date +%Y%m%d-%H%M%S', returnStdout: true).trim()

                    // AWS ECR에 로그인 후 Docker 이미지 빌드 및 푸시
                    docker.withRegistry("https://${ECR_URL}", 'awsCredentials') {
                        // ECR 리포지토리 주소와 IMAGE_NAME을 사용하여 이미지 빌드
                        app = docker.build("${ECR_URL}/${ECR_REPOSITORY}:${imageName}")
                        // 빌드된 이미지를 ECR에 푸시
                        app.push("${imageName}")
                    }

                    // 푸시 후 로컬에서 해당 이미지를 삭제
                    sh "docker rmi ${ECR_URL}/${ECR_REPOSITORY}:${imageName}"
                }
            }
        }
    }
}
