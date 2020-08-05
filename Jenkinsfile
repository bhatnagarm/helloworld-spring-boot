def project = 'helloworld-spring-boot-demo'
def appName = 'helloworld-spring-boot'
def imageTag = "localhost:5000/${appName}:${env.BUILD_NUMBER}"
def imageTagOnce = "localhost:5000/${appName}:1.0.0-SNAPSHOT"

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
  - name: docker
    image: docker:latest
    command:
    - cat
    tty: true
    volumeMounts:
    - mountPath: /var/run/docker.sock
      name: docker-sock
  volumes:
    - name: docker-sock
      hostPath:
        path: /var/run/docker.sock
"""
        }
    }
    stages {
      stage('Build artifact') {
        steps {
          container('maven') {
            sh 'mvn clean install'
          }
        }
      }
      stage('Build docker container') {
        steps {
            container('docker') {
                sh "docker build -t ${imageTagOnce} ."
                sh "docker push ${imageTagOnce}"
                sh "echo \"imageTag = ${imageTagOnce}\" > build.properties"
                sh "echo \"appName = ${appName}\" > build.properties"
                sh "echo \"appVersion = 1.0.0-SNAPSHOT\" > build.properties"
                archiveArtifacts "build.properties"
            }
        }
      }
    }
}