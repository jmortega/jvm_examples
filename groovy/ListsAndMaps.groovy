fruit = 'grapes'
list1 = [1,2,3]
list2 = ['apple','pear', fruit]

list3 = list1 + list2
// No IndexOutOfBounds, the list just grows
list2[3] = 'orange'
println list3

// Inserting an element to the end of the list
list1 << list2
println list1

list1[3][5] = ['x','y']
println list1
println list1.flatten()

//We've been changing list2 because what list1 actually keeps is just a reference
println list2

// We can subtract lists, we get a new list object
list4 = list1.flatten() - list2
println list4

// We can apply a closure to every element to create a new one.
// "it" is the list's element in every iteration 
list5 = list4.collect {it + 1}
println list5

// We can filter using regexps
println list5.grep(~/\d+/)

// No slicing ? sad...
//println list4[1:3]

fruitPrices = ["apple":20,"orange":25,"pear":30]

//Just like javascript
assert fruitPrices["apple"] == 20
println fruitPrices.apple

// an empty map
empty = [:]
assert empty.size() == 0

//We can use string keys without quotes
morePrices = [apple:20,orange:25,pear:30]

morePrices.grape = 50

println morePrices

veg = [pea:1, carrot:10]

// maps support plus operators
println morePrices + veg

colorKey = 1
//Using () we can set any variable value as key
colors = [0:"Red", (colorKey):"Green"]
println colors[1]





