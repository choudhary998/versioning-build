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
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'harsh',
                    url: 'https://github.com/choudhary998/versioning-build.git'

                sh "ls -lat"
                println "***********************************OLDER_VERSION version is ${env.COMMIT_VERSION}"
                echo 'Build steps are in progress!!!'
            }
        }
        stage('Versioning') {
            steps {
                echo 'Bumping up the version!!!'
                sh "bash set_version.sh"
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'harsh', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD']]) {
                    sh '''
                        git add .
                        git commit -m "Latest version pushed into the file version.txt"
                        git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/choudhary998/versioning-build.git
                        git status
                    '''
                }
            }
        }
        stage('Git Checkout 2') {
            steps {
                git branch: 'master',
                    credentialsId: 'harsh',
                    url: 'https://github.com/choudhary998/versioning-build.git'

                sh "ls -lat"
                sh "cat version.txt"
            }
        }
        stage('Git Tag Stage') {
            steps {
                script {
                    def IMAGE_TAG = readFile(file: 'version.txt')
                    println(IMAGE_TAG)
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'harsh', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD']]) {
                        sh "git tag v${IMAGE_TAG}"
                        // sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/choudhary998/versioning-build.git ${IMAGE_TAG}"
                    }
                }
            }
        }
    }
}