@k8s.ClosureArray
package io.meshrun.cfg.example

def redisLabels = {
  app 'redis'
  role 'master'
  tier 'backend'
}

def deployment = new k8s.apps.v1.Deployment("redis-master", {
  metadata {
    namespace "default"
    labels {
      app 'redis'
    }
  }
  spec {
    selector {
      matchLabels redisLabels
    }
    replicas 1
    template {
      metadata {
        labels redisLabels
      }
      spec {
        containers {
          -{
            name "master"
            image "redis"
            resources {
              cpu "100m"
              memory "100Mi"
            }
            ports {
              -{ containerPort 6379 }
              -{ containerPort 6379 }
            }
          }
        }
      }
    }
  }
})

deployment.metadata["namespace"] = "default"
print(deployment)
