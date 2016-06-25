package com.jessitron.fpwj.m3;

import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ReadFromStdIn {

  public static void main(String[] args) {
    readFromStdin("Knock knock. ").anyMatch(s -> s.equals("Who's there?"));
    System.out.println("Boo.");
    readFromStdin("").anyMatch(s -> s.equals("Boo who?"));
    System.out.println("You don't have to cry about it.");
  }

  private static Stream<String> readFromStdin(String prompt) {
    Scanner scanner = new Scanner(System.in);
    Supplier<String> stdin = () -> {
      System.out.print(prompt);
      return scanner.nextLine();
    };
    return Stream.generate(stdin);
  }
}
