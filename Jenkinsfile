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
        ECS_SERVICE = 'drinkhere-ecs-service'  // ECS 서비스 이름
        ECS_CLUSTER = 'DrinkhereCluster'  // ECS 클러스터 이름
        ECS_TASK_DEFINITION = 'drinkhere-ecs-td'  // ECS 작업 정의 이름
        CODEDEPLOY_APPLICATION = 'AppECS-DrinkhereCluster-drinkhere-ecs-service' // CodeDeploy 애플리케이션 이름
        CODEDEPLOY_DEPLOYMENT_GROUP = 'DgpECS-DrinkhereCluster-drinkhere-ecs-service' // CodeDeploy 배포 그룹 이름
    }

    stages {

        stage('Checkout SCM with Submodules') {
            steps {
                // 서브모듈도 포함하여 클론
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    extensions: [
                        [$class: 'SubmoduleOption', recursiveSubmodules: true] // 서브모듈 재귀적으로 클론
                    ],
                    userRemoteConfigs: [[
                        url: 'https://github.com/DrinkHere/backend.git',
                        credentialsId: 'githubCredentials'
                    ]]
                ])
            }
        }

        stage('Build Spring Boot Project to Jar File') {
            steps {
                sh './gradlew clean :execute:bootJar' // Gradle 빌드
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

        stage('Build Docker Image & Push to ECR') {
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

                    env.IMAGE_NAME = imageName
                }
            }
        }

        stage('Update Drinkhere ECS Task Definition') {
            steps {
                script {
                    // `env.IMAGE_NAME`을 사용하여 ECS Task Definition에 최신 이미지 반영
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: "${AWS_CREDENTIAL_NAME}"]]) {
                        sh "aws ecs describe-task-definition --task-definition $ECS_TASK_DEFINITION --query 'taskDefinition' > task-definition.json"
                        sh "jq '.containerDefinitions[0].image = \"${ECR_URL}/${ECR_REPOSITORY}:${env.IMAGE_NAME}\"' task-definition.json > updated-task-definition.json"
                    }
                }
            }
        }

//         stage('Deploy to ECS with Blue/Green Deployment') {
//             steps {
//                 script {
//                     // AWS 자격 증명이 설정된 상태에서 AWS CLI 명령어 실행
//                     withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: "${AWS_CREDENTIAL_NAME}"]]) {
//                         // ECS 서비스 업데이트
//                         def updateService = sh(script: """
//                             aws ecs update-service --cluster $ECS_CLUSTER \
//                                 --service $ECS_SERVICE \
//                                 --task-definition file://updated-task-definition.json
//                         """, returnStdout: true).trim()
//
//                         echo "ECS Service updated: ${updateService}"
//
//                         // CodeDeploy 배포 실행
//                         def deploy = sh(script: """
//                             aws deploy create-deployment \
//                                 --application-name $CODEDEPLOY_APPLICATION \
//                                 --deployment-group-name $CODEDEPLOY_DEPLOYMENT_GROUP \
//                                 --revision file://updated-task-definition.json \
//                                 --description "Deployment for ${env.IMAGE_NAME}"
//                         """, returnStdout: true).trim()
//
//                         echo "CodeDeploy deployment initiated: ${deploy}"
//                     }
//                 }
//             }
//         }
    }
}
