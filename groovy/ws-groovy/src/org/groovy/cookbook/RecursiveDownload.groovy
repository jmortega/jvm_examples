
download(new URL("http://groovy.codehaus.org/gapi/groovy/json/JsonOutput.html"), new File('output'))

def download(URL url, File dir) {

  def outputFile = new File(dir, url.getFile() ?: "index.html")
  outputFile.delete()
  println "Saving to file: $outputFile"

  println "Creating directory: $dir"
  outputFile.parentFile.mkdirs()

  // TODO: handle different file types differently
  url.eachLine { line ->
    def matches = line =~ /href=(["'])([^"']+)(["'])/
    matches.each { match ->
      String link = match[2]
      println "Found link: $link"
      // TODO: parse links better
      if (link.startsWith("http")) {
        download(new URL(link), new File(dir, url.getFile()))
      } else {
        download(new URL(url.toString() + '/' + link), new File(dir, url.getFile()))
      }
    }
    outputFile << line
  }
}

