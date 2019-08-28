res = { values ->
  return [
      new k8s.apps.v1.Deployment("${values.name}-dep", {
        spec {
          template {
            labels {
              app values.app
            }
            spec {
              containers {
                -{
                  name values.app
                  image "${values.image.repository}:${values.image.tag}"
                }
              }
            }
          }
        }
      }),

      new k8s.core.v1.Service("${values.name}-svc", {
        metadata {
          labels {
            app values.app
          }
          spec {
            selector {
              app values.app
            }
          }
        }
      })
  ]
}
