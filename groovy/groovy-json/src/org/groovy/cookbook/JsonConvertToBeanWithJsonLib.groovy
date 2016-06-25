package org.groovy.cookbook

import org.groovy.cookbook.Vehicle

@Grab(group='net.sf.json-lib', module='json-lib', version='2.3', classifier='jdk15')
import net.sf.json.JSONObject

def jsonObject = JSONObject.fromObject(new File('vehicle.json').text)

def vehicle = JSONObject.toBean(jsonObject, Vehicle)

println vehicle.getClass()
println vehicle