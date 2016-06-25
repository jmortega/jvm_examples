package com.jessitron.fp4j.m2.collections;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class ExampleConvertedFromGuava {

  final static String[] food = new String[] {
          "Crunchy carrots",
          "Golden-hued bananas",
          "",
          "Bright orange pumpkins",
          "Little trees of broccoli",
          "meat"
  };

  private static String summarize(String[] descriptions) {
    return Arrays.asList(descriptions).stream().
                    filter(s -> !s.isEmpty()). // skip some
                    map(lastWord). // transform
                    reduce(joinOn(" & ")).
                    orElse("");
  }

  private static BinaryOperator<String> joinOn(String divider) {
        return (allSoFar, oneMore) ->
                allSoFar + divider + oneMore;
  }


  private static BinaryOperator<String> last =
          (other, last) -> last;

  private static Function<String,String> lastWord = (String phrase) ->
     Arrays.asList(phrase.split(" ")).stream().
                    reduce(last).
                    orElse("");

  public static void main(String[] args) {
    final String summary = summarize(food);

    final String desired =  "carrots & bananas & pumpkins & broccoli & meat";
    System.out.println(summary);
    if (summary.equals(desired)) System.out.println("yay!");
  }
}
