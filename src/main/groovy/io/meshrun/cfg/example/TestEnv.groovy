@k8s.ClosureArray
package io.meshrun.cfg.example

redash_service = {
  image "redash/redash:latest"
  depend_on {
    -"postgres"
    -"redis"
  }
  restart 'always'
}

envs = ["DEBUG=true", "TEST=1"]
def compose = new compose.v2.File({
  services {
    server redash_service >> {
      command "server"
      ports {
        -"5000:5000"
      }
      environment {
        -"QUEUES=true"
        -"REDASH_WEB_WORKERS=4"
        +envs
      }
    }
  }
})

print(compose)
