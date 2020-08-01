def project = 'helloworld-spring-boot-demo'
def appName = 'helloworld-spring-boot'
def imageTag = "http://localhost:5000/${appName}:${env.BUILD_NUMBER}"

pipeline {
    agent {
        kubernetes {
            label 'Java-build'
            defaultContainer 'jnlp'
            yaml """
apiVersion: v1
kind: Pod
metadata:
labels:
    component: ci
    spec:
        # Use service account that can deploy to all namespaces
        containers:
        - name: maven
          image: maven:alpine
          command:
          - cat
          tty: true"""
        }
    }
    stages {
      stage('Run maven') {
        steps {
          container('maven') {
            sh 'mvn clean compile'
          }
        }
      }
      stage('Test') {
        steps {
          container('maven') {
            sh 'mvn clean verify'
          }
        }
      }
    }
}