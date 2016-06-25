package org.groovy.cookbook

class Book extends Publication {

  @Delegate
  PrintedPublication printedFeatures = new PrintedPublication()
  
}

