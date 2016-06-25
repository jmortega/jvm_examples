package com.jessitron.fp4j.m2.timing;

import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Timing {

    public static <A> A timed(String description,
                              Supplier<A> code) {
        Consumer<String> defaultOutput = System.out::println;
        return timed(description, defaultOutput, code);
    }

    public static <A> A timed(String description,
                              Consumer<String> output,
                              Supplier<A> code) {

        final Date before = new Date();
        A result = code.get();
        final Long duration = new Date().getTime() -
                before.getTime();
        output.accept(description + " took " + duration
                + " milliseconds");

        return result;
    }
}
