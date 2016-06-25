@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.6' )
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*
import groovyx.gpars.GParsPool

class CriminalDataService {


  List getData(List countries) {
    def response = []
    GParsPool.withPool {

      countries.each() { country ->
        def x =  this.&fetchData.callAsync(country)
        println x.class.name
        response << x
      }
    }

    return response
  }


  def fetchData(String country) {
    def http = new HTTPBuilder('http://localhost:5050')
    def jsonData
    def start = System.currentTimeMillis()
    http.request( GET, JSON ) {
      uri.path = "/${country}"
      response.success = { resp, json ->
        jsonData = json
      }
    }
    jsonData.put('fetch-time', (System.currentTimeMillis() - start))
    jsonData
  }


}

CriminalDataService cda = new CriminalDataService()
def start = System.currentTimeMillis()
def data = cda.getData(['germany', 'us', 'canada'])
assert 3 == data.size()

data.each {
  println "data received: ${it.get()}"
}
println "Total execution time: ${System.currentTimeMillis() - start} ms."
