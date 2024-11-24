pipeline {
  agent any

  tools {
    jdk ('jdk21')  // Java 21 사용.
  }

  environment {
    ECR_URL = '481665105550.dkr.ecr.ap-northeast-2.amazonaws.com'  // ECR URL
    ECR_REPOSITORY = 'drinkhere/spring-server'  // ECR 리포지토리 이름
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm  // 소스 코드 체크아웃
      }
    }

    stage('Build') {
      steps {
        sh 'java -version'  // Java 21 버전 확인
        sh './gradlew clean build'  // Gradle 빌드
      }
    }

    stage('Push image') {
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
