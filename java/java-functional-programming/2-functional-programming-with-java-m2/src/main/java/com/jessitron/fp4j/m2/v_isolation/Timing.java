package com.jessitron.fp4j.m2.v_isolation;

import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Timing {

    public static <A> A timed(String description,
                              Supplier<A> code) {
       // final Consumer<String> defaultPrinter = System.out::println;
        final Consumer<String> defaultPrinter = (s) -> {};  //change default to do nothing
        return timed(description, defaultPrinter, code);
    }


    public static <A> A timed(String description,
                              Consumer<String> printer,
                              Supplier<A> code) {
        final Date before = new Date();

        final A result = code.get();

        final Long duration = new Date().getTime() - before.getTime();
        printer.accept(description + " took " + duration + " ms");
        return result;
    }
}
