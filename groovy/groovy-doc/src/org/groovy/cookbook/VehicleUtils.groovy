package org.groovy.cookbook

/**
 * This class contains vehicle related calculation utility methods. 
 * 
 * @author Groovy Writer
 * @since 2013 
 * 
 */
class VehicleUtils {

  /**
   * Returns average fuel consumption per 100 km. More information about 
   * involved calculations can be found in the
   * <a href="http://en.wikipedia.org/wiki/Fuel_efficiency">respected source</a>.  
   *
   * @param distance 
   *           the distance driven by the vehicle.
   * @param liters 
   *           the amount of fuel spent in liters.
   *  
   * @return calculated fuel consumption.
   *  
   */
  static def fuelConsumptionPer100Km(distance, liters) {
    (liters * 100) / distance    
  } 

}                            