// HTTP REQUESTS
def page = new URL("http://groovy.codehaus.org/").text
// println page


def url = new URL("http://groovy.codehaus.org/")
def connection = url.openConnection()
if(connection.responseCode == 200){
  println connection.content.text
} else {
  println "An error occurred: ${connection.responseCode} ${connection.responseMessage}"
}

assert connection.responseCode == 200
assert connection.contentType == "text/html; charset=UTF-8"
println connection.lastModified
connection.headerFields.each { println "> ${it}"}

println "http://www.google.com?q=groovy".toURL().text

def gurl = "http://www.google.com?"
def queryString = [q: 'jvm languages', pws: '0', num: '50', cr: 'US']
def googleQuery = gurl + queryString.collect { k,v -> "$k=${URLEncoder.encode(v)}" }.join('&')

println googleQuery
