package org.groovy.cookbook

import static groovyx.gpars.GParsPool.runForkJoin
import static groovyx.gpars.GParsPool.withPool
import static com.google.common.collect.Lists.*
import java.util.concurrent.ConcurrentHashMap


class WordAnalyzer2 {

  static final Integer THRESHOLD = 50000
  Map<String,Integer> frequencies = new ConcurrentHashMap<String,Integer>()

  private void calculateFrequency(List<String> _words) {

    _words.each() {
      Integer num = frequencies.get(it)
      if (num!=null) {
        frequencies.put(it,num+1)
      } else {
        frequencies.put(it,1)
      }
    }


  }

  Map frequency(List<String> tokens) {
    println "no parallel: size is ${tokens.size}"
    calculateFrequency(tokens)
    frequencies.sort { a,b -> b.value <=> a.value }
  }


  Map frequencyFJ(List<String> tokens) {


    println "size is ${tokens.size}"
    def maps

    withPool(4) {
      runForkJoin(tokens) {  words ->

        if (words.size() <= THRESHOLD) {
          // No parallelism
          println "enter direct"
          return calculateFrequency(words)
        } else {
          partition(words, THRESHOLD).each() { sublist ->
            forkOffChild ( sublist )
          }
          // Collect all results
          //return childrenResults
        }

      }
    }

    frequencies.sort { a,b -> b.value <=> a.value }

  }
}