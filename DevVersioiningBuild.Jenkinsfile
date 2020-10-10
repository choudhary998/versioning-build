def getVersion() {
    script {
        return sh(script: "cat version.txt", returnStdout: true)
    }
}

pipeline {
    agent any     
    environment {
        DEFAULT_VERSION = "0.0.${env.BUILD_ID}"
        COMMIT_VERSION = getVersion()
    }
    parameters {
        text(name: 'DEV_ACCOUNT_ID', defaultValue: '494272177199', description: 'Enter the AWS Account ID of Dev Environment')
        string(name: 'REGION', defaultValue: 'us-east-1', description: 'Enter the Region')
        string(name: 'DEV_REPO_NAME', defaultValue: 'automated-fargate-poc-repo', description: 'Enter the AWS ECR Repo name pertaining to Dev Environment')
    }
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'harsh',
                    url: 'https://github.com/choudhary998/automated-dev-update-poc.git'

                sh "ls -lat"
            }
        }
        stage('SBT Build') {
            steps {
                println "***********************************OLDER_VERSION version is ${env.COMMIT_VERSION}"
                echo 'Build steps are in progress!!!'
                // sh '''SBT_VERSION=1.3.13
                //       sbt test
                //       sbt "runMain example.Hello"
                //       sbt stage
                //    '''
            }
        }
        // stage('ECR Login Stage') {
        //     steps {
        //         echo 'Login into the DEV Account ECR'
        //         sh "aws ecr get-login-password --region ${params.REGION} | tail -n +1 | sudo docker login --username AWS --password-stdin  ${params.DEV_ACCOUNT_ID}.dkr.ecr.${params.REGION}.amazonaws.com"
        //     }
        // }
        // stage('Docker Build') {
        //     steps {
        //         echo 'Build the Docker Image'
        //         sh "sudo docker build -t ${params.DEV_REPO_NAME} . "
        //     }
        // }
        stage('Versioning') {
            steps {
                echo 'Bumping up the version!!!'
                sh "sudo bash set_version.sh"
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'harsh', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD']]) {
                    sh '''
                        git add .
                        git commit -m "Latest version pushed into the file version.txt"
                        git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/choudhary998/automated-dev-update-poc.git
                        git status
                    '''
                }
            }
        }
        stage('Git Checkout 2') {
            steps {
                git branch: 'master',
                    credentialsId: 'harsh',
                    url: 'https://github.com/choudhary998/automated-dev-update-poc.git'

                sh "ls -lat"
                sh "cat version.txt"
            }
        }
        stage('Docker Tag and Push Stage') {
            steps {
                script {
                    def IMAGE_TAG = readFile(file: 'version.txt')
                    println(IMAGE_TAG)
                    sh '''
                        git tag ${IMAGE_TAG}
                        git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/choudhary998/automated-dev-update-poc.git ${IMAGE_TAG}
                        git status
                    '''                    
                    // echo 'Tag and Push the Docker Image to ECR'
                    // sh "sudo docker tag ${params.DEV_REPO_NAME} ${params.DEV_ACCOUNT_ID}.dkr.ecr.${params.REGION}.amazonaws.com/${params.DEV_REPO_NAME}:${IMAGE_TAG}"
                    // sh "sudo docker push ${params.DEV_ACCOUNT_ID}.dkr.ecr.${params.REGION}.amazonaws.com/${params.DEV_REPO_NAME}:${IMAGE_TAG}"
                }
            }
        }
    }
}