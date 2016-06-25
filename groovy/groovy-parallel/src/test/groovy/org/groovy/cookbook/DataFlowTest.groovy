package org.groovy.cookbook

import org.junit.*
import edu.stanford.nlp.process.PTBTokenizer
import edu.stanford.nlp.process.CoreLabelTokenFactory
import edu.stanford.nlp.ling.CoreLabel
import org.groovy.cookbook.actors.stateless.*
import org.groovy.cookbook.dataflow.*

class DataFlowTest {

  @Test
  void testFrequency() {
    
    def criminalService = new CriminalServiceWithDataflow('http://localhost:5050')
    def data = criminalService.getData(['germanys', 'us', 'canada'])
    assert 3 == data.size()

    data.each() {
      try {
        println it
      } catch (e) {
        e.printStackTrace()
      }
    }
  }
  
}


