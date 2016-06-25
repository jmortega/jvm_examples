package com.jessitron.fp4j.m2.iv_generalizeType;

import java.util.Date;
import java.util.function.Supplier;
import java.util.function.Consumer;

public class Timing {

    public static <A> A timed(String description,
                              Supplier<A> code) {
        final Date before = new Date();

        final A result = code.get();

        final Long duration = new Date().getTime() - before.getTime();
        System.out.println(description + " took " + duration + " ms");
        return result;
    }
}
