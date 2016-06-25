import groovy.xml.StreamingMarkupBuilder


def xmlSource = new File('shakespeare.xml')
def bibliography = new XmlSlurper().parse(xmlSource)


bibliography.appendNode {
  'lit:play' {
    'lit:year'(1598) 
    'lit:title'("Much Ado About Nothing.")    
  }
}


def output = new StreamingMarkupBuilder()



println output.bind{ mkp.yield bibliography }