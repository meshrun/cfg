//
// This is an example of using an external library, put in ./libs.
// All jar files under ./libs, relative to the invoked script, will be used as the class paths.
//
// joda-time.jar in this case could be used right away as following:
//

REDASH_VERSION = System.getenv("REDASH_VERSION") ?: "${org.joda.time.LocalDate.now()}"
envs = []

redash_service = {
  image "redash/redash:${REDASH_VERSION}"
  depend_on([
      "postgres",
      "redis"
  ])
  restart 'always'
}

def compose = new compose.v2.File({
  services {
    server redash_service >> {
      command "server"
      ports([
          "5000:5000"
      ])
      environment([
          "REDASH_WEB_WORKERS=4"
      ])
    }
    scheduler redash_service >> {
      command "scheduler"
      environment([
          "QUEUES=celery",
          "WORKERS_COUNT=1"
      ] + envs)
    }
    scheduled_worker redash_service >> {
      command "worker"
      environment([
          "QUEUES=scheduled_queries,schemas",
          "WORKERS_COUNT=1"
      ] + envs)
    }
    adhoc_worker redash_service >> {
      command "worker"
      environment([
          "QUEUES=queries",
          "WORKERS_COUNT=2"
      ] + envs)
    }
    redis {
      image "redis:5.0-alpine"
      restart "always"
    }
    postgres {
      image 'postgres:9.5-alpine'
      environment envs
      volumes([
          '$PWD/postgres-data:/var/lib/postgresql/data'
      ])
      restart "always"
    }
    nginx {
      image "redash/nginx:latest"
      ports([
          "80:80"
      ])
      depends_on([
          "server"
      ])
      links([
          "server:redash"
      ])
      restart "always"
    }
  }
})

print(compose)