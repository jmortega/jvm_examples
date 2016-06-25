
def currentDir = new File('.')

println "-------------------------"
println "Test1"
println "-------------------------"

currentDir.eachFileMatch(/.*\.txt/) { File file ->
  println file.name
}


println "-------------------------"
println "Test2"
println "-------------------------"

currentDir.traverse { File file ->
  if (file.name.matches(/.*\.groovy/)) {
    println file.name
  }
}


println "-------------------------"
println "Test3"
println "-------------------------"

currentDir.traverse(nameFilter: ~/.*\.groovy/) { File file ->
  println file.name
}

println "-------------------------"
println "Test4"
println "-------------------------"

currentDir.traverse(
  nameFilter: ~/.*\.groovy/,
  excludeNameFilter: ~/^C.*$/) { File file ->
    println file.name
}



println "-------------------------"
println "Test5"
println "-------------------------"
  
currentDir.traverse(
  nameFilter:        { it.matches(/.*\.groovy/) },
  excludeNameFilter: { it.matches(/^C.*$/)      }, 
  visit:             { println it.name          }
)


println "-------------------------"
println "Test6"
println "-------------------------"
  
def today = new Date()

currentDir.traverse(
  filter:            { it.lastModified() > (today - 5).time &&
                       it.name.endsWith('.groovy')             },
  excludeFilter:     { it.isDirectory()                        },
  visit:             { println it.name                         }
)

