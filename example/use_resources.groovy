import resources

defaults = [
    name: "helloworld",
    app:  "hello",
    image: [
        repository: "weaveworks/helloworld",
        tag: "v1"
    ]
]

res = require(resources)

res(defaults).each { r ->
    println("---")
    print(r)
}
