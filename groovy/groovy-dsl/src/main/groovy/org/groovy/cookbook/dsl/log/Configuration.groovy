package org.groovy.cookbook.dsl.log

class Configuration {

  def String format

  private final columnNames = [:]
  private final columnIndexes = [:]
  private final sources = [:]
  private final reports = [:]

  private static int sourceCounter = 0
  private static int reportCounter = 0

  def void format(String format) {
    this.format = format
  }

  def void column(int group, String name) {
    columnNames[group] = name
    columnIndexes[name] = group
  }

  def void source(Closure cl) {
    def generatedName = "source${sourceCounter++}"
    source(generatedName, cl)
  }

  def void source(String name, Closure cl) {
    Source source = new Source(name)
    cl.delegate = source
    cl.resolveStrategy = Closure.DELEGATE_FIRST
    cl()
    sources[name] = source
  }

  def void report(Closure cl) {
    def generatedName = "report${reportCounter++}"
    report(generatedName, cl)
  }

  def void report(String name, Closure cl) {
    Report report = new Report(name)
    cl.delegate = report
    cl.resolveStrategy = Closure.DELEGATE_FIRST
    cl()
    reports[name] = report
  }
}
