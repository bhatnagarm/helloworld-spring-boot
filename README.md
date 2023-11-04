# Sample Java Application 

## Overview
This application is a sample application which works is designed to be 
cloud native and work with Kuberneties. It has build setup to be able to 
run with Jenkins and be canary deployed with Spinnaker.

### Application uses
* Spring-boot 3.1.1
* Jdk 17
* Jenkins Kuberneties Agent
* Kubernetes


### Starting the application
    mvn clean verify
    docker build -t localhost:5000/helloworld .
    docker push localhost:5000/helloworld
    kubectl apply -f k8s/configmap/helloworld-backend-configmap.yaml
    kubectl apply -f k8s/backend-production.yaml
Is Istio enabled 
    
    kubectl apply -f <(istioctl kube-inject -f k8s/backend-production.yaml)
    kubectl apply -f k8s/services/backend-service-production.yaml

### Config management and security
It is always better to get the developers to have configuration where they can use it comfortably. So this application 
keeps the secrets in configuration. Just encrypt it using a secret different in each environment. Keep these away from 
developers to secure environments. We are using jaspt to encrypt secret. You cannot run the app if you don't have the 
password. I'm using configmap to keep the main password but you can secure it using Vault or any other password 
kubernetes secret products. 
