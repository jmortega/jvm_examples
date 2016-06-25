package org.groovy.cookbook.intercept


class InterceptMethod {

    def doStuff() {

        long start = System.currentTimeMillis()

        println "h"

        println "Execution time: ${(System.currentTimeMillis() - start)}"

    }


    public static void main(String[] args) {
        def i = new InterceptMethod();
        i.doStuff()

        def sc = new SlowClass()
        sc.test()

    }




}
