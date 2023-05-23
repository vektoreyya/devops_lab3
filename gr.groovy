pipeline {
    agent any

    environment{
        def imageName = "image_name"
        def dockerfile = "https://github.com/vektoreyya/devops_lab1/Dockerfile" // шлях до Dockerfile
        def dockerRegistry = "docker_registry"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and push Docker Image') {
            steps {
                script {                  
                    // збір Docker-образу
                    sh "docker build -t ${imageName} -f ${dockerfile} ."

                    // завантаження Docker-образу до Docker-реєстру
                    withDockerRegistry([url: dockerRegistry, credentialsId: 'docker_credentials']) {
                        sh "docker push ${imageName}"
                    }
                }
            }
        }
    }
}