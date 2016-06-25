getHello = {return "Hello"}
getHelloName = {name -> return "Hello " + name}

// prints the closure reference
println getHello
def hello = getHello
// the same reference
println hello

// when a function or closure takes no arguments we need to supply parentheses
println hello()

// also when method calls are nested
println getHelloName ("Pi")

// return keyword is optional, groovy will always return what the last
// statement produces

String myString(name) {
	"Hi there " + name
}

println myString("Pi")

//The result type could be dynamic
def myString2(name) {
	"Hi there " + name
}

println myString2("Pi")

// groovy behaves as if primitives did not exist. Even number literals behave
// like objects o_O
println 2.0.class.name

// Just like JavaScript we can use " or ' in string literals
String singleQuote = "A 'single' quoted String"
String doubleQuote = 'A "double" quoted String'

// Multi-line strings are allowed using """ or '''
println '''This is a multiline
string, and that
is all.
'''

// when using double quoted strings we can include arbitrary expressions using ${...}
sql = """
select * from user
where name = '${doubleQuote}';
"""
println sql

// simple example of regexp in groovy

def quickBrownFox = """
The quick brown fox
jumps over the lazy
dog.
"""
// Using slashy strings for creating a regular expression
matcher = quickBrownFox =~ /\b.o.\b/
matcher.each { match -> println match }
