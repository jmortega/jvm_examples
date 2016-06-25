class Customer {
  Long id
  String name
  String lastName
}

def c3 = new Customer()
c3.metaClass.fullName { return "$name $lastName" }
c3.name = 'John'
c3.lastName = 'Ross'


assert c3.fullName() == 'John Ross'


Customer.metaClass.constructor << {
  String name -> new Customer(name: name)
} << {Long id, String fullName ->
  new Customer(id:id,
               name:fullName.split(',')[0],
               lastName:fullName.split(',')[1])
}

def c0 = new Customer('Mike')
c0.name = 'Mike'

def c1 = new Customer(1000, "Mike,Whitall")
assert c1.name == 'Mike'
assert c1.lastName == 'Whitall'




