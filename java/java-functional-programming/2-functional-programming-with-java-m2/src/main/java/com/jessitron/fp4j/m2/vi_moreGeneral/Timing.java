package com.jessitron.fp4j.m2.vi_moreGeneral;

import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Timing {

    public static class Duration {
        public final long ms;

        public Duration(long ms) {
            this.ms = ms;
        }
        // todo: structural equality
    }

    private static String defaultFormat(String description, Duration d) {
        return description + " took " + d + " ms";
    }

    public static <A> A timed(String description,
                              Supplier<A> code) {
       // final Consumer<String> defaultPrinter = System.out::println;
        final Consumer<String> defaultPrinter = (s) -> {};  //change default to do nothing
        return timed(
                (d) -> defaultPrinter.accept(defaultFormat(description, d)),
                code);
    }


    public static <A> A timed(Consumer<Duration> printer,
                              Supplier<A> code) {
        final Date before = new Date();

        final A result = code.get();

        final Duration duration = new Duration(new Date().getTime() - before.getTime());
        printer.accept(duration);
        return result;
    }
}
