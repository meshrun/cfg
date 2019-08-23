REDASH_VERSION = System.getenv("REDASH_VERSION") ?:"7.0.0.b18042"

redash_service = {
    image "redash/redash:${REDASH_VERSION}"
    depend_on([
        "postgres",
        "redis"
    ])
    env_file '$PWD/env'
    restart  'always'
}

def compose = new compose.v2.File({
    services {
        server(redash_service >> {
            command "server"
            ports([
                "5000:5000"
            ])
            environment([
                "REDASH_WEB_WORKERS=4"
            ])
        })
        scheduler(redash_service >> {
            command "scheduler"
            environment([
                "QUEUES=celery",
                "WORKERS_COUNT=1"
            ])
        })
        scheduled_worker(redash_service >> {
            command "worker"
            environment([
                "QUEUES=scheduled_queries,schemas",
                "WORKERS_COUNT=1"
            ])
        })
    }
})

print(compose)