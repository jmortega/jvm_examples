package org.groovy.cookbook.actors.stateless

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
final class WordFrequencyActor extends DynamicDispatchActor {

  void onMessage(CalculateFrequencyMessage message) {
    reply new FrequencyMapMessage(frequencyMap:calculateFrequency(message.tokens))
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


final class FrequencyMaster extends DynamicDispatchActor {
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

  void onMessage(FrequencyMapMessage frequenceMapMessage) {
    if (frequenceMapMessage.frequencyMap) {
      println ":::::: got a frequency map. Content is " + frequenceMapMessage.frequencyMap.size()
      totalFreqMap.putAll(frequenceMapMessage.frequencyMap)
      doneLatch.countDown()
    } 
  }

  void onMessage(StartFrequency startFrequency) {
    beginFrequency()
    startupLatch.countDown()
    println ":::::: Start Frequency Operation"
  }


}


class ActorBasedWordAnalyzerStateless {

  Map frequency(List<String> tokens) {
    def master = new FrequencyMaster(tokensList: tokens, numActors: 5).start()
    master << new StartFrequency() 
    return master.waitUntilDone()
    //println m.size()
  }



}

