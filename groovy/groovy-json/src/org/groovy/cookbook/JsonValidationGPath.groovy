package org.groovy.cookbook

import groovy.json.JsonSlurper

def reader = new File('vehicle.json').newReader()
def vehicle = new JsonSlurper().parse(reader)

def isValidTransmission(Object vehicle) {
  vehicle?.transmission?.type in [ 'MANUAL', 'AUTOMATIC' ] &&
  vehicle?.transmission?.gears > 3
}

def isValidFuel(Object vehicle) {
  vehicle?.fuel in [ 'DIESEL', 'PETROL', 'GAS', 'ELECTRIC']
}

def hasWheels(Object vehicle) {
  vehicle?.wheels?.size() > 0
}

def isValidTireProtector(Object vehicle) {
  vehicle?.wheels*.protector?.depth?.every { it > 2 } ?: false 
}


println 'Vehicle has valid transmission data: ' + isValidTransmission(vehicle)
println 'Vehicle has valid fuel:              ' + isValidFuel(vehicle)
println 'Vehicle has wheels:                  ' + hasWheels(vehicle)
println 'Tire protector depth is good:        ' + isValidTireProtector(vehicle)

