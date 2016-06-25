package com.jessitron.fp4j.m2.collections;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;

import java.util.Arrays;

public class Example {

  final static String[] food = new String[] {
          "Crunchy carrots",
          "Golden-hued bananas",
          "",
          "Bright orange pumpkins",
          "Little trees of broccoli",
          "meat"
  };
  public static final Predicate<String> NON_EMPTY =
          new Predicate<String>() {
    @Override
    public boolean apply(String s) {
      return !s.isEmpty();
    }
  };

  private static String summarize(String[] descriptions) {

    final Iterable<String> lastWords =
            FluentIterable.from(Arrays.asList(descriptions)).
            filter(NON_EMPTY).   // skip some
            transform(lastWord);  // transform

    // relationship between elements
    String output = Joiner.on(" & ").join(lastWords);

    return output;
  }

  private static Function<String, String> lastWord =
          new Function<String, String>() {
            @Override
            public String apply(String phrase) {
              Iterable<String> words = Arrays.asList(phrase.split(" "));
              return Iterables.getLast(words);
            }
          };

  public static void main(String[] args) {
    final String summary = summarize(food);

    final String desired =  "carrots & bananas & pumpkins & broccoli & meat";
    System.out.println(summary);
    if (summary.equals(desired)) System.out.println("yay!");
  }
}
