package org.groovy.cookbook.soap

import wslite.soap.*

class SoapTest extends GroovyTestCase {

  void testMLKDay() {

    def client = new SOAPClient("http://www.holidaywebservice.com/Holidays/US/Dates/USHolidayDates.asmx?WSDL")
    def response = client.send(SOAPAction:'http://www.27seconds.com/Holidays/US/Dates/GetMartinLutherKingDay') {
      body {
        GetMartinLutherKingDay('xmlns':'http://www.27seconds.com/Holidays/US/Dates/') {
          year(2013)
        }
      }
    }

    def date = new Date().parse("yyyy-MM-dd'T'hh:mm:ss", 
      response.GetMartinLutherKingDayResponse.GetMartinLutherKingDayResult.text())

    assertEquals(date, new Date().parse("yyyy-MM-dd", "2013-01-15"))
    assertToString("ASP.NET",response.httpResponse.headers['X-Powered-By'] )


  }


  void testMLKDayAsString() {

    def client = new SOAPClient("http://www.holidaywebservice.com/Holidays/US/Dates/USHolidayDates.asmx?WSDL")

    def response = client.send(
      """<?xml version='1.0' encoding='UTF-8'?>
       <soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:dat='http://www.27seconds.com/Holidays/US/Dates/'>
        <soapenv:Header/>
        <soapenv:Body>
            <dat:GetMartinLutherKingDay>
              <dat:year>2013</dat:year>
            </dat:GetMartinLutherKingDay>
        </soapenv:Body>
      </soapenv:Envelope>"""

      )



    def date = new Date().parse("yyyy-MM-dd'T'hh:mm:ss", 
      response.GetMartinLutherKingDayResponse.GetMartinLutherKingDayResult.text())

    assertEquals(date, new Date().parse("yyyy-MM-dd", "2013-01-15"))
    assertToString("ASP.NET",response.httpResponse.headers['X-Powered-By'] )


  }


}