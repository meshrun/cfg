@k8s.ClosureArray
package io.meshrun.cfg.example

def myapp = new vks.vmware.com.v1.KubernetesCluster("my-application", {
    metadata {
        namespace "default"
    }
    spec {
        topology {
            workers {
                count  3
                class_ "small"
            }
        }
        distribution "1.14.1"
    }
})

print(myapp)