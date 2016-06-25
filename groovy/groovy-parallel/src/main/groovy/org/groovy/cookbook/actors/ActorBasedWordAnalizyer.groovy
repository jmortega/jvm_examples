package org.groovy.cookbook.actors

import groovyx.gpars.actor.*

import java.util.concurrent.CountDownLatch
import static com.google.common.collect.Lists.*


final class CalculateFrequencyMessage {
  List<String> tokens
}

final class StartFrequency {

}
final class StopFrequency {

}

final class FrequencyMapMessage {
  Map frequencyMap

}
final class WordFrequencyActor extends DefaultActor {

  void act() {
        loop {
            react {message ->
                switch (message) {
                    case CalculateFrequencyMessage:
                      println "received message on thread ${Thread.currentThread().name}"
                      reply new FrequencyMapMessage(frequencyMap:calculateFrequency(message.tokens))
                }
            }
        }
  }

  private Map calculateFrequency(List<String> _words) {

    Map<String,Integer> frequencies = new HashMap<String,Integer>()

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


}


final class FrequencyMaster extends DefaultActor {
  static final Integer THRESHOLD = 2000
  Map totalFreqMap = [:]
  List<String> tokensList = []
  int numActors = 1

  private CountDownLatch startupLatch = new CountDownLatch(1)
  private CountDownLatch doneLatch

  private List createWorkers() {

    return (1..numActors).collect {new WordFrequencyActor().start()}
  }

  private void beginFrequency() {
    def slaves = createWorkers()
    int cnt = 0
    def partitioned = partition(tokensList, THRESHOLD)
    partitioned.each() { sublist ->
      if (cnt >= numActors) cnt = 0 // reset actor count
      slaves[cnt % numActors] << new  CalculateFrequencyMessage(tokens:sublist)
      cnt+=1

    }
    doneLatch = new CountDownLatch(partitioned.size())
    
  }

  public Map waitUntilDone() {
      startupLatch.await()
      doneLatch.await()
      return totalFreqMap
  }



  void act() {
    loop {
      react {
        switch(it) {
          case FrequencyMapMessage:
            if (it.frequencyMap) {
              println ":::::: got a frequency map. Content is " + it.frequencyMap.size()
              totalFreqMap.putAll(it.frequencyMap)
              doneLatch.countDown()
            } 
            break
          case StartFrequency:
            beginFrequency()
            startupLatch.countDown()
            println ":::::: Start Frequency Operation"
            break
          default:
            throw new RuntimeException("Invalid message type: ${it.class.name}")

        }
      }
    }

  }


}


class ActorBasedWordAnalyzer {

  Map frequency(List<String> tokens) {
    def master = new FrequencyMaster(tokensList: tokens, numActors: 5).start()
    master << new StartFrequency() 
    Map m = master.waitUntilDone()
    println m.size()
  }



}

