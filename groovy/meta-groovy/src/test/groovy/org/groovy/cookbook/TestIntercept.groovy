package org.groovy.cookbook

import org.junit.*
import org.groovy.cookbook.intercept.*

class TestIntercept {

  @Test
  void methodIsIntercepted() {

    SlowClass sc = new SlowClass()
    (1..3).each {
      sc.test("hello")
    }

  }

  @Ignore void methodIsInterceptedByUsingProxy() {
    useInterceptor( InterceptedClass, PerformanceInterceptor ) {
      def ic = new InterceptedClass()
      ic.test("a")
      ic.test("b")
      ic.test("c")
    }

  }


  def useInterceptor = { Class theClass, Class theInterceptor, Closure theCode ->

    def proxy = ProxyMetaClass.getInstance( theClass )
    def interceptor = theInterceptor.newInstance()
    proxy.interceptor = interceptor
    proxy.use( theCode )
  }

}