@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.6')
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*

def baseUrl = 'http://www.holidaywebservice.com/' +
    'Holidays/US/Dates/USHolidayDates.asmx'

def client = new HTTPBuilder(baseUrl)

// client.setProxy("127.0.0.1", 8087, "http")

def holidayBase = 'http://www.27seconds.com/Holidays'
def holidayNS = "$holidayBase/US/Dates/"
def soapAction = "$holidayBase/US/Dates/GetMothersDay"
def soapNS = 'http://schemas.xmlsoap.org/soap/envelope/'

def response = client.request( POST, XML ) {
  headers =
      [
        'Content-Type': 'text/xml; charset=UTF-8',
        'SOAPAction': soapAction
      ]
  body = {
    mkp.pi(xml:'version="1.0" encoding="UTF-8"')
    'soap-env:Envelope'('xmlns:soap-env': soapNS) {
      'soap-env:Header'()
      'soap-env:Body' {
        GetMothersDay('xmlns': holidayNS) { year(2013) }
      }
    }
  }
}

println response.getClass()
println response as String
new XmlNodePrinter(preserveWhitespace: true).print(response)
