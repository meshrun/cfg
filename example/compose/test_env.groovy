envs = new File("${SCRIPT_HOME}/env").readLines()

redash_service = {
    image "redash/redash:${REDASH_VERSION}"
    depend_on([
            "postgres",
            "redis"
    ])
    restart  'always'
}

def compose = new compose.v2.File({
    services {
        server redash_service >> {
            command "server"
            ports {
              - "5000:5000"
            }
            environment {
                -"REDASH_WEB_WORKERS=4"
            }
        }
    }
})