@Grab('org.ccil.cowan.tagsoup:tagsoup:1.2')
def tagsoupParser = new org.ccil.cowan.tagsoup.Parser()
def parser = new XmlSlurper(tagsoupParser)
def html = parser.parseText(new File("result.html").text)

html.body.'**'.findAll { it.@class == 'r' }.each { item ->
  println item.a.text()
}
