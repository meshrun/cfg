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
for(Object r: res(defaults)) {
  println("---")
  print(r)
}
