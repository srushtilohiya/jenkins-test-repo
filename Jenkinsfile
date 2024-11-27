pipeline {
    agent any

    tools {
        git 'Git'
        maven 'Maven 3'
        nodejs 'Node 18'
    }

    environment {
        GITHUB_CREDENTIALS = 'github-token'  // GitHub credentials ID
        EC2_SSH_KEY = 'Ubuntu EC2 Instance'  // EC2 SSH Key credential ID
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Ensure GitHub SSH key is in known_hosts
                    sh '''#!/bin/bash
                        # Add GitHub's SSH key to known_hosts to avoid Host key verification failed error
                        ssh-keyscan github.com >> ~/.ssh/known_hosts
                    '''
                    // Checkout the repository
                    git 'https://github.com/srushtilohiya/jenkins-test-repo.git'
                }
            }
        }

        stage('Install Dependencies') {
            steps {
                script {
                    // Install Maven dependencies
                    sh 'mvn clean install'  // For Java dependencies
                }
            }
        }

        stage('Build App') {
            steps {
                script {
                    // Build Java application using Maven
                    sh 'mvn package'  // This will compile the Java code and package it into a .jar file

                    // If you have a frontend component, you can also build with npm
                    // sh 'npm run build'  // Uncomment this if you have a frontend
                }
            }
        }

        stage('Run Unit Tests') {
            steps {
                script {
                    // Run tests with Maven
                    sh 'mvn test'  // This will execute unit tests using Maven
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
                    // SSH into EC2 and deploy the app (ensure your EC2 has Java & Node.js installed)
                    sshagent(credentials: ['Ubuntu EC2 Instance']) {
                        sh '''#!/bin/bash
                        # Add GitHub's SSH key to known_hosts to avoid Host key verification failed error
                        ssh-keyscan github.com >> ~/.ssh/known_hosts

                        # SSH into EC2 and pull latest changes
                        git clone git@github.com:srushtilohiya/jenkins-test-repo.git
                        ssh -o StrictHostKeyChecking=no ubuntu@3.110.136.70 <<EOF
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
