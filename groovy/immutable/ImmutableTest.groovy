import groovy.transform.Immutable
@Immutable class Customer {

    String name, lastName
    Long id;
}

def c1 = new Customer ('Mark','Hogan', 100)
def c2 = new Customer (lastName:'Hogan',id:200,name:'Mark')
c1.name = 'John'

// Should fail with groovy.lang.ReadOnlyPropertyException