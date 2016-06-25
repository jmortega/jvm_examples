
File file = new File('poem.txt')

int wordCount = 0
file.splitEachLine(/[\s\t]+/) { Collection words ->
  words.findAll{ it.trim() }.each { String word ->
    wordCount++
    println word
  }
}
println "Number of words: $wordCount"
