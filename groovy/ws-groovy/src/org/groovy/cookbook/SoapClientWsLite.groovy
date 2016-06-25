@Grab('com.github.groovy-wslite:groovy-wslite:0.7.1')
import wslite.soap.*

// def proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress('127.0.0.1', 8087))

def baseUrl = 'http://www.holidaywebservice.com/' +
    'Holidays/US/Dates/USHolidayDates.asmx'

def client = new SOAPClient(baseUrl)

def holidayBase = 'http://www.27seconds.com/Holidays'
def holidayNS = "$holidayBase/US/Dates/"
def soapAction = "$holidayBase/US/Dates/GetMothersDay"

def response = client.send(/*proxy: proxy,*/ SOAPAction: soapAction) {
  body {
    GetMothersDay('xmlns': holidayNS) {  year(2013) }
  }
}

// println response.httpRequest
println response.GetMothersDayResponse
