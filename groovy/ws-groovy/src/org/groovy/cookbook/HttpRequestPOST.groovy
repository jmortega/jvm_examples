@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.6')
import groovyx.net.http.*

// POST
def google = new URL("http://httpbin.org/post")
def con = google.openConnection()
con.doOutput = true
con.setRequestMethod("POST")
def queryString = "q=groovy+jvm"
Writer writer = new OutputStreamWriter(con.outputStream)
writer.write(queryString)
writer.flush()
writer.close()
con.connect()

println con.content.text


def post(String baseUrl, String path, query, method = Method.POST) {

  try {
    def ret = null
    def http = new HTTPBuilder(baseUrl)

    // perform a POST request, expecting TEXT response
    http.request(method, ContentType.TEXT) {
      uri.path = path
      uri.query = query
      headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'

      // response handler for a success response code
      response.success = { resp, reader ->

        ret = reader.text
      }
    }
    return ret

  } catch (groovyx.net.http.HttpResponseException ex) {
    ex.printStackTrace()
    return null
  } catch (java.net.ConnectException ex) {
    ex.printStackTrace()
    return null
  }
}

println post('http://www.httpbin.org', '/post', [q:"groovy"])
