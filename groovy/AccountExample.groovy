
// The script's name can not be Customer nor Account
class Customer {
	int id
	String name
	String toString(){
		"Name: ${name} Id: ${id}"
	}
}
class Account {
	int id
	double balance
	Customer owner
	
	void credit (double deposit) {
		balance += deposit
	}
	String toString() {
		"Account id ${id} owner ${owner.name} balance is ${balance}"
	}
}

// The code outside class definition will be wrapped in a third class.
// The variables are added to this binding scope, so we can start using them.
customer = new Customer(id:1,name:"Aaron Anderson")
savings = new Account(id:2, balance:0.00, owner:customer)
savings.credit 2

println savings

/*class AccountExample {
	public static void main (args) {
		// In this case we use "def" to bind variables to the scope.
		def customer = new Customer(id:1,name:"Aaron Anderson")
		def savings = new Account(id:2, balance:0.00, owner:customer)
		savings.credit 20.00
		println savings
	}
}*/

// groovy creates getters/setters methods to access object's properties. 
customer.name = "Usain Bolt"
customer.setId 3
println customer

// we can pass a map to a constructor
map = [id: 1, name: "Barney, Rubble"]
customer1 = new Customer( map )
customer2 = new Customer( id: 2, name: "Fred, Flintstone")


