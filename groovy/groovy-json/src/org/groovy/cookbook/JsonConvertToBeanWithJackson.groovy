package org.groovy.cookbook

import groovy.json.JsonOutput

import org.groovy.cookbook.Vehicle
import org.groovy.cookbook.Vehicle.Transmission

import static org.groovy.cookbook.Vehicle.FuelType.*
import static org.groovy.cookbook.Vehicle.TransmissionType.*

@Grab('com.fasterxml.jackson.core:jackson-databind:2.1.0')
import com.fasterxml.jackson.databind.ObjectMapper

ObjectMapper mapper = new ObjectMapper()

def vehicle = new Vehicle(
    brand: 'Mazda',
    model: 5,
    transmission: new Transmission(
      gears: 5, 
      type: MANUAL),
    releaseYear: 2007,
    fuel: PETROL)

println JsonOutput.prettyPrint(mapper.writeValueAsString(vehicle))

def file = new File('vehicle.json')
println mapper.readValue(file, Vehicle)
