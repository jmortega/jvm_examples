
import groovy.io.FileType;

def currentDir = new File('.')

println "-------------------------"
println "Test1"
println "-------------------------"
currentDir.eachFile { File file ->
  println file.name 
  if (file.isDirectory()) {
    file.eachFile trampoline() 
  }
}

println "-------------------------"
println "Test1a"
println "-------------------------"
currentDir.eachFile { File file ->
  println file.name
  file.file ?: file.eachFile( trampoline() ) 
}

println "-------------------------"
println "Test2"
println "-------------------------"
currentDir.eachFileRecurse { File file ->
  println file.name
}

println "-------------------------"
println "Test2a"
println "-------------------------"
currentDir.eachDirRecurse { File dir ->
  println dir.name
}

println "-------------------------"
println "Test2b"
println "-------------------------"
currentDir.eachFileRecurse(FileType.FILES) { File file ->
  println file.name
}

println "-------------------------"
println "Test3"
println "-------------------------"
currentDir.traverse { File file ->
  println file.name
}


println "-------------------------"
println "Test4"
println "-------------------------"
currentDir.eachFileMatch(~/.*\.groovy/) {
  println file.name
}

