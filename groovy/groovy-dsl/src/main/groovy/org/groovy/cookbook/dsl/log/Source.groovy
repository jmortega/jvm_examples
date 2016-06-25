package org.groovy.cookbook.dsl.log

class Source {

  def String name
  def files = [] as Set

  Source(String name) {
    this.name = name
  }

  def void localFile(File file) {
    if (file) {
      files << file.absoluteFile.canonicalFile
    }
  }

  def void localFile(String file) {
    localFile(new File(file))
  }
}
