@Grab('net.sf.saxon:Saxon-HE:9.4')
@GrabExclude('xml-apis:xml-apis')            
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.*
import net.sf.saxon.lib.NamespaceConstant

def TODOs = '''<?xml version="1.0" ?>
    <todos>
        <task created="2012-09-24" owner="max">
            <title>Buy Milk</title>
            <priority>3</priority>
            <location>WalMart</location>
            <due>2012-09-25</due>
            <alarm-type>sms</alarm-type>
            <alert-before>1H</alert-before>
        </task>
        <task created="2012-09-27" owner="rick">
            <title>Pay the rent</title>
            <priority>1</priority>
            <location>Computer</location>
            <due>2012-09-30</due>
            <alarm-type>email</alarm-type>
            <alert-before>1D</alert-before>
        </task>
        <task created="2012-09-21" owner="lana">
            <title>Take out the trash</title>
            <priority>3</priority>
            <location>Home</location>
            <due>2012-09-22</due>
            <alarm-type>none</alarm-type>
            <alert-before/>
        </task>    
    </todos>'''


def inputStream = new ByteArrayInputStream(TODOs.bytes)
def myTodos     = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream).documentElement


println '\n filter by date (Using Saxon): \n'


System.setProperty('javax.xml.xpath.XPathFactory:' + NamespaceConstant.OBJECT_MODEL_SAXON, 'net.sf.saxon.xpath.XPathFactoryImpl')
def xpathSaxon = XPathFactory.newInstance(XPathConstants.DOM_OBJECT_MODEL).newXPath()


xpathSaxon.evaluate( "//task[xs:date(due) = xs:date('2012-09-22') ]/title/text()", myTodos, XPathConstants.NODESET ).each { println it.nodeValue }

xpathSaxon.evaluate( "//task[xs:date(due) < xs:date('2012-09-30') ]/title/text()", myTodos, XPathConstants.NODESET ).each { println it.nodeValue }
