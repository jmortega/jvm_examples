@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.6')
import groovyx.net.http.URIBuilder

def baseUri = 'http://www.google.com/one/two?a=1#frag'
def uri = new URIBuilder(baseUri)

uri.with {
  scheme = 'https'
  host = 'localhost'
  port = 8080
  fragment = 'asdf2'
  path = 'three/four.html'
  path = '../four/five'
  query = [a: 2, b: "x"]
}

uri.addQueryParam("a", "3")

println uri.toURI()
println uri.toURL()
println uri.toString()