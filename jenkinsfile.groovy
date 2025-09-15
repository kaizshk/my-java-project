pipeline {
    agent any
    tools { maven 'Maven3' }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/your-username/my-java-project.git', credentialsId: 'github-creds'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package -B'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test -B'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
    post {
        always {
            echo "Pipeline finished!"
        }
    }
}
