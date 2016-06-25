import groovy.xml.StreamingMarkupBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.*

def TODOs = '''<?xml version="1.0" ?>
    <todos>
        <task created="2012-09-24" owner="max">
            <title>Buy Milk</title>
            <priority>3</priority>
            <location>WalMart</location>
            <due>2012-09-25-06:00</due>
            <alarm-type>sms</alarm-type>
            <alert-before>1H</alert-before>
        </task>
        <task created="2012-09-27" owner="rick">
            <title>Pay the rent</title>
            <priority>1</priority>
            <location>Computer</location>
            <due>2012-09-30-06:00</due>
            <alarm-type>email</alarm-type>
            <alert-before>1D</alert-before>
        </task>
        <task created="2012-09-21" owner="lana">
            <title>Take out the trash</title>
            <priority>3</priority>
            <location>Home</location>
            <due>2012-09-22-12:00</due>
            <alarm-type>none</alarm-type>
            <alert-before/>
        </task>    
    </todos>'''


def inputStream = new ByteArrayInputStream(TODOs.bytes)
def myTodos     = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream).documentElement


def xpath = XPathFactory.newInstance().newXPath()

def nodes = xpath.evaluate( '//task', myTodos, XPathConstants.NODESET )

nodes.each {

    println xpath.evaluate('title/text()', it)
}


xpath.evaluate( '//task/title/text()', myTodos, XPathConstants.NODESET ).each { println it.nodeValue }

println '\n low priority: \n'

xpath.evaluate( '//task[priority >1]/title/text()', myTodos, XPathConstants.NODESET ).each { println it.nodeValue }

println '\n filter by attribute: \n'

xpath.evaluate( "//task[@owner='lana']/title/text()", myTodos, XPathConstants.NODESET ).each { println it.nodeValue }

println '\n filter by content: \n'

xpath.evaluate( "//task[location='Computer' and contains(alarm-type,'email')]/title/text()", myTodos, XPathConstants.NODESET ).each { println it.nodeValue }

xpath.evaluate( "//*[contains(.,'WalMart')]/title/text()", myTodos, XPathConstants.NODESET ).each { println it.nodeValue }
