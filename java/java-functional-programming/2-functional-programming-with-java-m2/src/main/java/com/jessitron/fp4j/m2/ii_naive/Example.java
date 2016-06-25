package com.jessitron.fp4j.m2.ii_naive;

import java.util.Random;
import java.util.Date;

public class Example {

  public static void main(final String[] args) {

    final Date beforeCosts = new Date();
    final Double costs = calculateCosts();
    final Long costsDuration =
            new Date().getTime() - beforeCosts.getTime();
    System.out.println(
            "Cost calculation took " + costsDuration + " ms");


    final Date beforeRevenue = new Date();
    final Double revenue = calculateRevenue();
    final Long revenueDuration =
            new Date().getTime() - beforeRevenue.getTime();
    System.out.println(
            "Revenue calculation took " + revenueDuration + " ms");


    final Date beforeProfit = new Date();
    final Double profit = calculateProfit(costs, revenue);
    final Long profitDuration =
            new Date().getTime() - beforeProfit.getTime();
    System.out.println(
            "Profit calculation took " + profitDuration + " ms");

    System.out.println("Profit = $" + profit);

  }


  private static Double calculateCosts() {
    pretendToWorkHard();
    return 4567.3;
  }

  private static Double calculateRevenue() {
    pretendToWorkHard();
    return 23413.2;
  }

  private static Double calculateProfit(Double costs, Double revenue) {
    pretendToWorkHard();
    return revenue - costs;
  }

  private static final Random r = new Random();
  private static final Integer MAX_WORK_TIME_MS = 2000;

  private static void pretendToWorkHard() {
    try {
      Thread.sleep(r.nextInt(MAX_WORK_TIME_MS));
    } catch (InterruptedException e) {
    }
  }
}
