package org.groovy.cookbook
import org.groovy.cookbook.ast.*
import org.junit.*

class TestCacheableAST  {

  @Test
  void testInvokeUnitTest() {

    def gp = new GuineaPig()

    (1..3).each {
      withTime { println gp.expensiveMethod(10) }
    }

  }

  def withTime = {Closure operation ->

    def start = System.currentTimeMillis()
    operation()
    println 'TIME IS MS. > ' + (System.currentTimeMillis() - start)
  }

}
