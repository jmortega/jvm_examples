package org.groovy.cookbook

@Grapes([
  @Grab(group='net.sf.json-lib', module='json-lib', version='2.3', classifier='jdk15'),
  @Grab('xom:xom:1.2.5')
])
import net.sf.json.JSONObject
import net.sf.json.xml.XMLSerializer

def ui = JSONObject.fromObject(new File('ui.json').text)
def vehicle = JSONObject.fromObject(new File('vehicle.json').text)

println new XMLSerializer().write(ui)
println '=============='
println new XMLSerializer().write(vehicle)
