import groovy.transform.*

@AutoClone() class Vehicle {

    String brand
    String type
    Long wheelsNumber
    Engine engine
}

class Engine {
  int horseEngine
  Number litre

}