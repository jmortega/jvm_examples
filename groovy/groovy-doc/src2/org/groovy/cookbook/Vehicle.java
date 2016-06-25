package org.groovy.cookbook;

/**
 * This class contains vehicle data structure. 
 * 
 * @author Groovy Writer
 * @since 2013 
 * 
 */
public class Vehicle {

  final double maxSpeed;

  public Vehicle(double maxSpeed) {
    this.maxSpeed = maxSpeed;
  }

  /**
   * Returns vehicle's maximum speed.
   */
  public double getMaxSpeed() {
    return maxSpeed;
  }

}                            