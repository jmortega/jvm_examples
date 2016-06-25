package org.groovy.cookbook

import groovy.transform.ToString

@ToString
class Vehicle {

  static enum FuelType { DIESEL, PETROL, GAS, ELECTRIC } 
  static enum TransmissionType { MANUAL, AUTOMATIC }
  
  @ToString
  static class Transmission {
    long gears
    TransmissionType type
  }
  
  String brand
  String model
  FuelType fuel
  
  Long releaseYear
  
  Transmission transmission
  
}