package org.groovy.cookbook.intercept

import groovy.util.logging.Slf4j

@Slf4j
class PerformanceInterceptor implements Interceptor {

  private Long start = 0

  Object beforeInvoke(Object object, String methodName, Object[] arguments){

    start = System.currentTimeMillis() 
    null //value returned here isn't actually used anywhere else

  }

  boolean doInvoke(){ true } //whether or not to invoke the intercepted method with beforeInvoke's copy of arguments

  Object afterInvoke(Object object, String methodName, Object[] arguments, Object result){

    log.debug("Execution time for method ${methodName}: ${(System.currentTimeMillis()-start)} ms. ")
    return result

  }
}