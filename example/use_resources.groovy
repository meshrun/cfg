import resources

defaults = [
  name: "helloworld",
  app:  "hello",
  image: [
    repository: "meshrun/helloworld",
    tag: "v1"
  ]
]

res = require(resources)
res(defaults).each { r ->
  println("---")
  print(r)
}
