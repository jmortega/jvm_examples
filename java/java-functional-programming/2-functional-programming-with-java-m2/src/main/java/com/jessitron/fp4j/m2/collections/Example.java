package com.jessitron.fp4j.m2.collections;

import java.util.*;

public class Example {

  final static List<String> food = Arrays.asList(
          "Crunchy carrots",
          "Golden-hued bananas",
          "",
          "Bright orange pumpkins",
          "Little trees of broccoli",
          "meat"
  );

  static String summarize(List<String> descriptions) {
    StringBuffer output = new StringBuffer();
    boolean isFirst = true;
    for (String d: descriptions) {
      if (!d.isEmpty()) {
        if (!isFirst) {
          output.append(" & ");
        }
        String lastWord = lastWord(d);
        output.append(lastWord);
        isFirst = false;
      }
    }

    return output.toString();
  }

  private static String lastWord(final String d) {
    final int lastSpaceIndex = d.lastIndexOf(" ");
    final String lastWord;
    if (lastSpaceIndex < 0) {
      lastWord = d;
    } else {
      lastWord = d.substring(lastSpaceIndex + 1, d.length());
    }
    return lastWord;
  }

  public static void main(String[] args) {
    final String summary = summarize(food);

    final String desired =  "carrots & bananas & pumpkins & broccoli & meat";
    System.out.println(summary);
    if (summary.equals(desired))
      System.out.println("yay!");
  }

}
