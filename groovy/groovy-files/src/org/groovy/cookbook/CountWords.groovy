
File file = new File('poem.txt')

int wordCount = 0
file.eachLine { String line ->
  line.tokenize().each { String word ->
    wordCount++
    println word 
  }
}
println "Number of words: $wordCount"