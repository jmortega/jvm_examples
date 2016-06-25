@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.6')
import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.XML
import groovyx.net.http.RESTClient

class LocationFinder {

  final static KEY = 'Agcl0EQCTkhZLLDuSr07n3knvOS1v86HJf0G8P8pVZ4_no93dZqG-zNskaod0l-p'
  final static URL = 'http://dev.virtualearth.net'

  public void getAddressData(countryCode, zip, city, address, xml=false) {

    def mapClient = new RESTClient( URL )

    def path = "REST/v1/Locations/${countryCode}/${zip}/${city}/${address}"
    println path
    def response = mapClient.get(path: path, queryString:'key='+KEY)


    assert ( response.data instanceof net.sf.json.JSON )
    assert response.status == 200
    assert response.contentType == JSON.toString()

    println response.data.resourceSets.resources.point.coordinates

    response.data.each {
      println it.key + " = " + it.value
    }
  }
}

LocationFinder rt = new LocationFinder()
rt.getAddressData('FR', '75007', 'Paris', "Leon Jost")
