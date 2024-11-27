pipeline {
    agent any

    tools {
        git 'Git'
        maven 'Maven 3'
        nodejs 'Node 18'
    }

    environment {
        GITHUB_CREDENTIALS = 'github-token'
        EC2_SSH_KEY = 'Ubuntu EC2 Instance'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/srushtilohiya/jenkins-test-repo.git'
            }
        }

        stage('Install Dependencies') {
            steps {
                script {
                    sh 'mvn clean install'
                }
            }
        }

        stage('Build App') {
            steps {
                script {
                    sh 'mvn package'
                }
            }
        }

        stage('Run Unit Tests') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
                    sshagent(credentials: ['Ubuntu EC2 Instance']) {
                        sh '''#!/bin/bash
                        # Check if the repository exists and pull the latest changes
                        if [ ! -d "jenkins-test-repo" ]; then
                            git clone https://github.com/srushtilohiya/jenkins-test-repo.git
                        else
                            cd jenkins-test-repo && git pull
                        fi

                        # SSH into the EC2 instance and deploy the app
                        ssh -o StrictHostKeyChecking=no ubuntu@43.204.231.114 <<EOF
                            cd /home/ubuntu/my-java-project/
                            git pull
                            mvn clean install
                            mvn package
                            java -jar target/my-java-project-1.0-SNAPSHOT.jar
                        EOF
                        '''
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Build and Deployment Successful'
        }
        failure {
            echo 'Build or Deployment Failed'
        }
    }
}
