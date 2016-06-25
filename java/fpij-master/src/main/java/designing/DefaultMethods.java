package designing;

/**
 * User: starblood
 * Date: 2014. 4. 17.
 * Time: 오전 10:27
 */
public class DefaultMethods {
    public interface Fly {
        default void takeOff() {
            System.out.println("Fly::takeOff");
        }
        default void land() {
            System.out.println("Fly::land");
        }
        default void cruise() {
            System.out.printf("Fly:cruise");
        }
        default void turn() {
            System.out.println("Fly:turn");
        }
    }

    public interface FastFly extends Fly {
        default void takeOff() {
            System.out.println("FastFly::takeOff");
        }
    }

    public interface Sail {
        default void cruise() {
            System.out.println("Sail::cruise");
        }
        default void turn() {
            System.out.println("Sail::turn");
        }
    }

    public class Vehicle {
        public void turn() {
            System.out.println("Vehicle::turn");
        }
    }


    public class SeaPlane extends Vehicle implements FastFly, Sail {
        private int altitude;

        public void cruise() {
            System.out.print("SeaPlan::cruise currently cruise like ");
            if (altitude > 0) {
                FastFly.super.cruise();
            } else {
                Sail.super.cruise();
            }
        }
    }

    public void useClasses() {
        SeaPlane seaPlane = new SeaPlane();
        seaPlane.takeOff();
        seaPlane.turn();
        seaPlane.cruise();
        seaPlane.land();
    }

    public static void main(final String[] args) {
        new DefaultMethods().useClasses();
    }
}
