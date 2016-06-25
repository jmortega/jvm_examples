// groovy does not have the classic for and do {} while kind of loops

// But we can use range (python style!)
// 0..10 is an inclusive range
for ( n in 0..10)
	println n

for ( x in ["apple", "orange", "pear"])
	println x

// here we use an exclusive range
for (c in 'a'..< 'g')
	println c

// ranges are objects defining next() previous() and implementing Comparable
// they have 2 properties: to and from
myRange = 1..10
assert myRange.to == 10
assert myRange.from == 1

// ranges implement java.util.List interface so we can use its methods
assert myRange.containsAll([2,4,7])
// we also can use the in keyword
assert 5 in myRange
