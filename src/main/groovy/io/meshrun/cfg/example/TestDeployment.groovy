@k8s.ClosureArray
package io.meshrun.cfg.example

def deployment = new k8s.apps.v1.Deployment("redis-master", {
    metadata {
        labels {
            app 'redis'
        }
    }
    ports {
        - {
            containerPort 6379
            name "a"
        }
        - {
            containerPort 6379
            name "b"
        }
    }
})

deployment.metadata["namespace"] = "default"
print(deployment)
