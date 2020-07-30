pipeline {
    agent {
        kubernetes {
            label 'Java-build'
            defaultContainer 'jnlp'
            yaml 'maven-pod.yaml'
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