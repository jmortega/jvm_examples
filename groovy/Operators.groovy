morePrices = [apple:20,orange:25,pear:30]

println morePrices.collect({it.key})
// Spread-dot operator
println morePrices*.value

class Name {
	def name
	def greet(greeting) {
		println greeting + " " + name
	}
}

def names = [ new Name(name:"Aaron"),
				new Name(name:"Bruce"),
				new Name(name:"Carol")]

// using spread-dot operator for invoking a method across all the elements in a list
names*.greet("Hello")

def greetAll(a,b,c){
	println "Hi ${a}, ${b} and ${c}"
}

// We can tear apart the elements of a list with the spread operator
greetAll(*names.name)
