def greet(greeting) {
	println greeting + ", World!"
}

// script methods can be called by name (being in the same script)
greet "Hello"
greet "Goodbye"
	
def fruits = ["apple", "orange" , "pear"]
likeornot = "like"
// we can assign closures to a variable and pass it like a parameter
// wherever it gets called from, it can see all that was defined in its scope 
def likeIt = { String fruit -> println "I ${likeornot} ${fruit} "}
fruits.each likeIt

likeornot = "dislike"
fruits.each likeIt

// since explicit types are optional we could write our closure :
def likeIt2 = { fruit -> println "I ${likeornot} ${fruit} "}
likeornot = "do not like"

fruits.each likeIt2