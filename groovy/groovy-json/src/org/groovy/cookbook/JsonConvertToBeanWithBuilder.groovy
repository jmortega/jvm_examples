package org.groovy.cookbook

import groovy.json.JsonSlurper

import org.groovy.cookbook.Vehicle
import org.groovy.cookbook.Vehicle.Transmission

import static org.groovy.cookbook.Vehicle.FuelType.*
import static org.groovy.cookbook.Vehicle.TransmissionType.*

def reader = new File('vehicle.json').newReader()
def jsonVehicle = new JsonSlurper().parse(reader)

def vehicle = new Vehicle(
    brand: jsonVehicle.brand,
    model: jsonVehicle.model,
    transmission: new Transmission(
      gears: jsonVehicle.transmission.gears, 
      type: jsonVehicle.transmission.type),
    releaseYear: jsonVehicle.releaseYear,
    fuel: jsonVehicle.fuel)

println vehicle
