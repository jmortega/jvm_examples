@Grab('com.thoughtworks.xstream:xstream:1.4.3')
import com.thoughtworks.xstream.*
import groovy.transform.TupleConstructor
import com.thoughtworks.xstream.io.xml.DomDriver

@TupleConstructor class Customer {    
    Long id
    String name
    String lastName
    Address businessAddress
}

@TupleConstructor class Address {
    String street
    String postcode
    String city
    String country
}

def xstream = new XStream()

def john = new Customer(100, 'John', 'Red', new Address('Ocean Drive 101', '33139', 'Miami', 'US'))

def xmlCustomer =  xstream.toXML(john)
def customer = new XmlSlurper().parseText(xmlCustomer)
assert customer.id == 100
assert customer.name == 'John'
assert customer.businessAddress.city == 'Miami'


def customerXml = '''
<Customer>
  <id>100</id>
  <name>John</name>
  <lastName>Red</lastName>
  <businessAddress>
    <street>Ocean Drive 101</street>
    <postcode>33139</postcode>
    <city>Miami</city>
    <country>US</country>
  </businessAddress>
</Customer>'''

def xstream2 = new XStream(new DomDriver())
Customer johnred = xstream2.fromXML(customerXml)

assert 100 == johnred.id
assert '33139' == johnred.businessAddress.postcode

// Using ALIAS

def xstream3 = new XStream()
xstream3.alias('Person', Customer)

println xstream3.toXML(john)

xstream3.aliasField('customer-id', Customer, 'id')

println xstream3.toXML(john)

def xstream4 = new XStream()
xstream4.useAttributeFor(Customer, 'id')

println xstream4.toXML(john)
