package org.groovy.cookbook.javatesting

import org.groovy.cookbook.StringUtil

class JavaTest extends GroovyTestCase {

  def stringUtil = new StringUtil()

  def testConcatenation() {
    String result = stringUtil.concat(['Luke', 'John'], '-')
    assertToString('Luke-John', result)

  }

  void testConcatenationWithEmptyList() {
    String result = stringUtil.concat([], ',')
    assertEquals('', result)

  }

  void testConcatenationWithNullShouldReturnNull() {
    String result = stringUtil.concat(null, ',')
    assertEquals('', result)
  }

  
  void testVerifyExceptionOnWrongSeparator() {
    shouldFail IllegalArgumentException, { stringUtil(['a','b'], ',,') }
    shouldFail IllegalArgumentException, { stringUtil(['a','b'], '') }
  }

}