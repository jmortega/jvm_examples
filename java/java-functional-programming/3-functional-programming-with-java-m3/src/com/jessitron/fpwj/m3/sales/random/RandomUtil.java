package com.jessitron.fpwj.m3.sales.random;

import java.util.Random;

class RandomUtil {

  private static Random random = new Random();

  static <A> A randomElement(A[] as) {
    int randomStore = random.nextInt(as.length);
    return as[randomStore];
  }
}
