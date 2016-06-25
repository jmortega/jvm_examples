if (![])
	println "[] evaluates to false"
if (!0)
	println "0 evaluates to false"
if (!'')
	println "'' evaluates to false"

x=0
// The elvis operator, shortcut for y=x?x:1
y = x?:1

println y