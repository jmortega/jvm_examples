@Grab('org.apache.httpcomponents:httpclient:4.2.1')
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet

def httpClient = new DefaultHttpClient()
def httpGet = new HttpGet("http://www.google.com/search?q=Groovy")

def httpResponse = httpClient.execute(httpGet)

new File("result.html").text = httpResponse.entity.content.text
