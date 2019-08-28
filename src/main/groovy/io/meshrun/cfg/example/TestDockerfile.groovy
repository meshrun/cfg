@k8s.ClosureArray
package io.meshrun.cfg.example

def s = ["a","b","c"]

new dockerfile.Dockerfile({
    /*
    FROM "ubuntu" as String

    RUN "apt-get install -y"

    ENTRYPOINT "abcd"
    ENTRYPOINT ["a", "b", "c"]
    ENTRYPOINT = s
    */
})