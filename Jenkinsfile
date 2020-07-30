pipeline {
    agent {
        kubernetes {
            label 'Java-build'
            defaultContainer 'jnlp'
            yaml '''apiVersion: v1
                    kind: Pod
                    metadata:
                      labels:
                        some-label: maven-builder
                    spec:
                      containers:
                        - name: maven
                          image: maven:alpine
                          command:
                            - cat
                          tty: true'''
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