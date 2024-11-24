pipeline {
  agent any

  tools {
    jdk 'jdk21'  // Java 21 사용.
  }

  environment {
    JAVA_HOME = 'tool jdk21'  // 환경 변수도 jdk21로 설정
    IMAGE_NAME = "${env.BRANCH_NAME.replace('/', '_')}_${getGitCommitPretty()}"  // 브랜치 이름과 Git 커밋 정보로 이미지 이름 생성
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
          // AWS ECR에 로그인 후 Docker 이미지 빌드 및 푸시
          docker.withRegistry("https://${ECR_URL}", 'awsCredentials') {
            // ECR 리포지토리 주소와 IMAGE_NAME을 사용하여 이미지 빌드
            app = docker.build("${ECR_URL}/${ECR_REPOSITORY}:${IMAGE_NAME}")
            // 빌드된 이미지를 ECR에 푸시
            app.push("${IMAGE_NAME}")
          }

          // 푸시 후 로컬에서 해당 이미지를 삭제
          sh "docker rmi ${ECR_URL}/${ECR_REPOSITORY}:${IMAGE_NAME}"
        }
      }
    }
  }
}
