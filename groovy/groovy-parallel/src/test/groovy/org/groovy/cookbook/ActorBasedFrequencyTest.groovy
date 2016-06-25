package org.groovy.cookbook

import org.junit.*
import edu.stanford.nlp.process.PTBTokenizer
import edu.stanford.nlp.process.CoreLabelTokenFactory
import edu.stanford.nlp.ling.CoreLabel
import org.groovy.cookbook.actors.stateless.*
import org.groovy.cookbook.actors.*

class ActorBasedFrequencyTest {

  @Test
  @Ignore
  void testFrequency() {
    def smallText = "http://www.gutenberg.org/cache/epub/17405/pg17405.txt".toURL()
    def bigText = "http://norvig.com/big.txt".toURL()
    def analyzer = new ActorBasedWordAnalyzer()
    analyzer.frequency(tokenize(bigText.text))
  
  }

  @Test
  void testFrequencyStateless() {
    def smallText = "http://www.gutenberg.org/cache/epub/17405/pg17405.txt".toURL()
    //def bigText = "http://norvig.com/big.txt".toURL()
    def analyzer = new ActorBasedWordAnalyzerStateless()
    Map res = analyzer.frequency(tokenize(smallText.text))
    res.each() {
      println "[ ${it.key} ${it.value} ]"
    }
  }


  def tokenize(String txt) {
      List<String> words = []
      PTBTokenizer ptbt = new PTBTokenizer(new StringReader(txt),
              new CoreLabelTokenFactory(), "")
      for (CoreLabel label; ptbt.hasNext(); ) {
        words << ptbt.next().value()
        //System.out.println(label)
      }
      words
  }



}


