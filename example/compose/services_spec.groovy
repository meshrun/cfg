import services

class spec extends spock.lang.Specification {

  def "test"() {
    when:
    def compose = apply(services)
    then:
    compose.version == "2"
  }

}