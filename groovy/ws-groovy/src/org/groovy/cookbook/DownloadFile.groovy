
def outputFile = new File("image.png")
def url = new URL("http://groovy.codehaus.org/images/groovy-logo-medium.png")

outputFile.delete()
url.withInputStream { inputStream ->  outputFile << inputStream }
