import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder

def writer = new StringWriter()
def xml = new MarkupBuilder(writer)

xml.bibliography {
  author('William Shakespare')
  play {
    year('1595')
    title('A Midsummer-Night’s Dream.')
  }
}

println writer



def builder = new StreamingMarkupBuilder()

def bibliography = builder.bind {
  bibliography {
    author('William Shakespare')
    play {
      year('1595')
      title('A Midsummer-Night’s Dream.')
    }
  }
}

println bibliography

