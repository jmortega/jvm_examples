package org.groovy.cookbook.ast

class GuineaPig {

  def cacheMap1 = [:]

  @Cacheable
  public Long veryExpensive(Long a) {

    sleep(rnd())
    a*20
  }

  public Long expensiveMethod( Long a ) {

    withCache (a) {

      sleep(rnd())
      a*5
    }
  }

  def withCache = {key, Closure operation ->

    if (!cacheMap1.containsKey(key)) {
      cacheMap1.put(key, operation())
    }
    cacheMap1.get(key)
  }

  static rnd() {

    Math.abs(new Random().nextInt() % 5000 + 1000)
  }
}
