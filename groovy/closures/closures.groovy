def doubling = { arg1 -> println arg1 * 2 }

println doubling

[1,2,3,4].each(doubling)
    