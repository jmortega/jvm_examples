package org.groovy.cookbook

import org.junit.*
import edu.stanford.nlp.process.PTBTokenizer
import edu.stanford.nlp.process.CoreLabelTokenFactory
import edu.stanford.nlp.ling.CoreLabel
import org.groovy.cookbook.stm.*

class StmTest {

  @Test
  void testFrequency() {
    def stm = new StmValueIncreaser()
    def results = stm.start()
    assert results.get("withStm") == 100
    assert results.get("noStm") != 100

    println "NO STM: " + results.get("noStm") 
  } 
}


