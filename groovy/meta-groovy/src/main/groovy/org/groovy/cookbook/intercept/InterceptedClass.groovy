package org.groovy.cookbook.intercept

class InterceptedClass  {

    void test(String a) {

        Thread.sleep(rnd())
    }


    static rnd() {

        Math.abs(new Random().nextInt() % 5000 + 1000)
    }


}
