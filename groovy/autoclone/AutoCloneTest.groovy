import groovy.transform.AutoClone

@AutoClone class Vehicle {

    String brand
    String type
    Long wheelsNumber
    Engine engine
}

class Engine {
  int horseEngine
  Number litre
}

def v1 = new Vehicle(brand:'Ferrari', type:'car', wheelsNumber:4)
def e1 = new Engine(horseEngine:390, litre:4.9)
v1.engine = e1
def v2 = v1.clone()

assert v1 instanceof Cloneable
assert !(v1.brand instanceof Cloneable)

