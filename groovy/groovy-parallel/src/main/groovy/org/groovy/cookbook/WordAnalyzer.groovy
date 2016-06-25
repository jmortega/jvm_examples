package org.groovy.cookbook

import static groovyx.gpars.GParsPool.runForkJoin
import static groovyx.gpars.GParsPool.withPool
import static com.google.common.collect.Lists.*
import java.util.concurrent.ConcurrentHashMap


class WordAnalyzer {

  static final Integer THRESHOLD = 500000

  private Map calculateFrequency(List<String> _words) {

    Map<String,Integer> frequencies = new ConcurrentHashMap<String,Integer>()
    
    _words.each() {
      Integer num = frequencies.get(it)
      if (num!=null) {
        frequencies.put(it,num+1)
      } else {
        frequencies.put(it,1)
      }
    }
    frequencies

  }

  Map frequency(List<String> tokens) {
    println "no parallel: size is ${tokens.size}"
    def f = calculateFrequency(tokens)
    f.sort { a,b -> b.value <=> a.value }
  }

  Map frequencyFJ(List<String> tokens) {

    Map<String,Integer> frequencyMap = [:]

    println "size is ${tokens.size}"
    def maps

    withPool(8) {
      maps = runForkJoin(tokens) {  words ->

        if (words.size() <= THRESHOLD) {
          // No parallelism
          println "enter direct"
          return calculateFrequency(words)
        } else {
          partition(words, THRESHOLD).each() { sublist ->
            forkOffChild ( sublist )
          }
          // Collect all results
          return childrenResults
        }

      }
    }
    maps.each() {

      frequencyMap.putAll(it)
    }
    frequencyMap.sort { a,b -> b.value <=> a.value }

  }
}