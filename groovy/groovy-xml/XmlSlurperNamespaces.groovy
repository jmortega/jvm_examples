
def xmlSource = new File('shakespeare.xml')
def bibliography = new XmlSlurper().parse(xmlSource)


println "1:"
println bibliography.'bib:author'

bibliography.declareNamespace(bib: 'http://bibliography.org', bob: 'http://bibliography.org', lit: 'http://literature.org')

println "2:"
println bibliography.'bib:author'

println "3:"
println bibliography.'bob:author'

println "4:"
println bibliography.'lit:play'.findAll { it.'lit:year'.toInteger() > 1592 }.size()


println "5:"
println bibliography.'*:author'

println "6:"
println bibliography.'lit:author'

println "7:"
println bibliography.author

println "8:"
println bibliography['author']

println "9:"
bibliography.play.findAll { it.year.toInteger() > 1592 }.each { println it.title }
  
println "10:"
println bibliography.'*:author'.text()
