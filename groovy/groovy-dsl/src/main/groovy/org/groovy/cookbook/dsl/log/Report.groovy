package org.groovy.cookbook.dsl.log

class Report {

  def String name

  def sumColumns = [] as Set
  def avgColumns = [] as Set
  def groupByColumns = [] as Set

  Report(String name) {
    this.name = name
  }

  def void sum(String columnName) {
    sumColumns << columnName
  }

  def void avg(String columnName) {
    avgColumns << columnName
  }

  def void groupBy(String columnName) {
    groupByColumns << columnName
  }
}
