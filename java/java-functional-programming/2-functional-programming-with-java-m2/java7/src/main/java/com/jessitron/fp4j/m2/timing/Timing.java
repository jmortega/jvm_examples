package com.jessitron.fp4j.m2.timing;

import java.util.Date;
import com.google.common.base.Supplier;
import com.google.common.base.Function;

public class Timing {

  private static Function<String, Void> DO_NOTHING = new Function<String, Void>(){
    @Override
    public Void apply(java.lang.String s) {
      return null;
    }
  };

  public static <A> A timed(String description,
                            Supplier<A> code) {
    return timed(description, DO_NOTHING, code);
  }

  public static <A> A timed(String description,
                            Function<String, Void> output,
                            Supplier<A> code) {

    final Date before = new Date();
    A result = code.get();
    final Long duration = new Date().getTime() -
            before.getTime();
    output.apply(description + " took " + duration
            + " milliseconds");

    return result;
  }
}
