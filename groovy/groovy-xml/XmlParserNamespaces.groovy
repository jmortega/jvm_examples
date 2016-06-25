import groovy.xml.Namespace

def xmlSource = new File('shakespeare.xml')
def bibliography = new XmlParser().parse(xmlSource)

def bib = new Namespace('http://bibliography.org', 'bib')
def lit = new Namespace('http://literature.org', 'lit')

println bibliography[bib.author].text()
println bibliography.'*:author'.text()
println bibliography[lit.play].findAll { it[lit.year].text().toInteger() > 1592 }.size()
