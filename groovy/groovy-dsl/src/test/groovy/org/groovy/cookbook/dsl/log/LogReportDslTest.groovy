package org.groovy.cookbook.dsl.log


import org.junit.Test

class LogReportDslTest {

  @Test
  public void testDsl() throws Exception {

    def engine = new LogReportDslEngine()

    engine.process {
      format '^execution of (\\w+) took (\\d+)ms$'
      column 1, 'methodName'
      column 2, 'duration'
      source('PerformanceData2012') {
        localFile 'log1.log'
        localFile 'log2.log'
      }
      source('PerformanceData2013') {
        localFile 'log2.log'
        localFile 'log3.log'
      }
      report('Duration') {
        avg 'duration'
        sum 'duration'
        groupBy 'methodName'
      }
    }
  }
}
