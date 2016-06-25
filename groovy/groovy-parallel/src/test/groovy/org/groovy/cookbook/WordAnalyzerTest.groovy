package org.groovy.cookbook

import org.junit.*
import edu.stanford.nlp.process.PTBTokenizer
import edu.stanford.nlp.process.CoreLabelTokenFactory
import edu.stanford.nlp.ling.CoreLabel
@Ignore
class WordAnalyzerTest {

  @Test
  void testFrequency() {
    def bigText = "http://norvig.com/big.txt".toURL() // token: 1297801

    //def bigText = "http://www.gutenberg.org/cache/epub/1532/pg1532.txt".toURL() //king lear, 39063 token

    //def wa = new WordAnalyzer2()
    def wa = new WordAnalyzer()

    def tokens = tokenize(bigText.text)
    long start = System.currentTimeMillis()

    def m1 = wa.frequency(tokens)
    println "TIME NO PAR: ${(System.currentTimeMillis() - start)} - ${m1.size()}"

    start = System.currentTimeMillis()

    def m = wa.frequencyFJ(tokens)
    println "TIME FJ: ${(System.currentTimeMillis() - start)} - ${m.size()}"


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