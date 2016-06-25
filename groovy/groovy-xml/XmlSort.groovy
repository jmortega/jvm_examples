import groovy.xml.*
// XSLT import
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

def groovyMoviez = '''<?xml version="1.0" ?>
        <movie-result>
            
            <movie>
                <title>Groovy Days</title>
                <imdb-id>tt0116288</imdb-id>
                <year>1996</year>
                <director>Peter Bay</director>
                <country>Denmark</country>
                <stars>Ken Vedsegaard, Sofie Gråbøl, Martin Brygmann</stars>
            </movie>
            <movie>
                <title>Born in 2005</title>
                <imdb-id>tt1492859</imdb-id>
                <year>2005</year>
                <director>Milo Sogueco</director>
                <country>Uganda</country>
                <stars/>
            </movie>
            <movie>
                <title>Cool and Groovy</title>
                <imdb-id>tt1189088</imdb-id>
                <year>1956</year>
                <director>Will Cowan</director>
                <country>USA</country>
                <stars>Anita Day, Buddy De Franco and Buddy DeFranco Quartet</stars>
            </movie>
            <movie>
                <title>Groovy: The Colors of Pacita Abad</title>
                <imdb-id>tt1492859</imdb-id>
                <year>2005</year>
                <director>Milo Sogueco</director>
                <country>Philippines</country>
                <stars/>
            </movie>
            
        </movie-result>
    '''

def sortXSLT = '''<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="@* | node()">
    <xsl:copy>
        <xsl:apply-templates select="@* | node()">
          <xsl:sort select="title"/>
        </xsl:apply-templates>
    </xsl:copy>
</xsl:template>

</xsl:stylesheet>'''

def list1 = ["cool and ", "groovy day", "groovy: The", "born"]
println list1
println list1.sort {it}

// Using Groovy groviness

def movieList = new XmlParser().parseText(groovyMoviez)

movieList.value = movieList.movie.sort{ it.'title'.text() }
println XmlUtil.serialize(movieList)

def movieList2 = new XmlParser().parseText(groovyMoviez)

movieList2.value = movieList2.movie.sort{ a,b -> a.year.text().toInteger() <=> b.year.text().toInteger() ?: a.country.text() <=> b.country.text()}

println XmlUtil.serialize(movieList2)

println "========================================="

// using XSLT

def factory = TransformerFactory.newInstance()
def transformer = factory.newTransformer(new StreamSource(new StringReader(sortXSLT)))
def result = new StreamResult(new StringWriter())
transformer.transform(new StreamSource(new StringReader(groovyMoviez)), result)

println result.writer.toString()
