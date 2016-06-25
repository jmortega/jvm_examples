@Grab('com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.1.0')
package org.groovy.cookbook

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import groovy.json.JsonSlurper

def uiReader = new File('ui.json').newReader()
def vReader = new File('vehicle.json').newReader()

def ui = new JsonSlurper().parse(uiReader)
def vehicle = new JsonSlurper().parse(vReader)

def mapper = new XmlMapper()

def vehicleWriter = new StringWriter()
def uiWriter = new StringWriter()

mapper.writeValue(uiWriter, ui)
mapper.writeValue(vehicleWriter, vehicle)

println uiWriter.toString()
println '\n==============\n'
println vehicleWriter.toString()

