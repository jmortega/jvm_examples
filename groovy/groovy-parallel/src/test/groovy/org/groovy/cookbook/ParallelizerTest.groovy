package org.groovy.cookbook

import static groovyx.gpars.GParsPool.*

import org.junit.*

import edu.stanford.nlp.process.PTBTokenizer
import edu.stanford.nlp.process.CoreLabelTokenFactory
import edu.stanford.nlp.ling.CoreLabel

class ParallelizerTest {

  static words = []

  @BeforeClass
  static void loadDict() {
    def libraryUrl = 'http://www.gutenberg.org/cache/epub/'
    def bookFile = '17405/pg17405.txt'
    def bigText = "${libraryUrl}${bookFile}".toURL()
    words = tokenize(bigText.text)
  }

  static tokenize(String txt) {
    List<String> words = []
    PTBTokenizer ptbt = new PTBTokenizer(
                          new StringReader(txt),
                          new CoreLabelTokenFactory(),
                          ''
                        )
    ptbt.each { entry ->
      words << entry.value()
    }
    words
  }

  @Test
  void combinedParalle() {
    withPool {
      println words
        .findAllParallel { it.length() > 10 &&
                           !it.startsWith('http') }
        .groupByParallel { it.length() }
        .collectParallel { "WORD LENGTH ${it.key}: ${it.value*.toLowerCase().unique()}" }
    }
  }

}