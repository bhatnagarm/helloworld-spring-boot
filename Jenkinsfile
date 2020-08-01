def project = 'helloworld-spring-boot-demo'
def appName = 'helloworld-spring-boot'
def imageTag = "localhost:5000/${appName}:${env.BUILD_NUMBER}"

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
    #serviceAccount: jenkins
  containers:
  - name: maven
    image: maven:3.6.3-openjdk-14-slim
    command:
      - /bin/sh
    tty: true
"""
        }
    }
    stages {
      stage('Build artifact') {
        steps {
          container('maven') {
            sh 'mvn clean install'
            sh "echo ${imageTag}"
          }
        }
      }
      /*stage('Build docker container') {
        steps {
            container('maven') {
                sh "docker build -t ${imageTag} ."
            }
        }
      }*/
    }
}