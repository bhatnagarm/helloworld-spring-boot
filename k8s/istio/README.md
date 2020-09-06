# Istio Configuration in Kubernetes
    curl -L https://istio.io/downloadIstio | sh -
    export PATH="$PATH:/mnt/d/Mitul_Workspace/cloud_native/istio-1.7.0/bin"

### Enable default namespace for default
    kubectl label namespace default istio-injection=enabled

If you do not want to make it default : 

        kubectl apply -f <(istioctl kube-inject -f samples/bookinfo/platform/kube/bookinfo.yaml)

else:

        kubectl apply -f samples/bookinfo/platform/kube/bookinfo.yaml
        kubectl apply -f samples/bookinfo/networking/bookinfo-gateway.yaml
        
Test Command:

    kubectl exec "$(kubectl get pod -l app=ratings -o jsonpath='{.items[0].metadata.name}')" -c ratings -- curl -s productpage:9080/productpage | grep -o "<title>.*</title>"

Check which namespaces are enabled with istio
    
    kubectl get namespace -L istio-injection 
    
Disable istio on namespace 

    kubectl label namespace default istio-injection=disabled --overwrite
    
