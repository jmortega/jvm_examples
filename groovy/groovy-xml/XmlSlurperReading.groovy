
def xmlSource = new File('shakespeare.xml')
def bibliography = new XmlSlurper().parse(xmlSource)

println bibliography.author
println bibliography['author']
bibliography.play.findAll { it.year.toInteger() > 1592 }.each { println it.title }
  

