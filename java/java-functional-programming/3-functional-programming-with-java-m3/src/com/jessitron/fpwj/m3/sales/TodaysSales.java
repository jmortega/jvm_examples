package com.jessitron.fpwj.m3.sales;

import com.jessitron.fpwj.m3.sales.random.RandomSale;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.*;
import java.util.stream.*;

public class TodaysSales {
  public static final List<Sale> sales = Arrays.asList(
          new Sale(Store.KANSAS_CITY, new Date(), Optional.of("Sarah"),
                  Arrays.asList(
                          new Item("carrot", 12.00)
                  )),
          new Sale(Store.ST_LOUIS, new Date(), Optional.empty(),
                  Arrays.asList(
                          new Item("carrot", 12.00),
                          new Item("lizard", 99.99),
                          new Item("cookie", 1.99)
                  )),
          new Sale(Store.ST_LOUIS, new Date(), Optional.of("Jamie"),
                  Arrays.asList(
                          new Item("banana", 3.49),
                          new Item("cookie", 1.49)
                  )));

  static Stream<Sale> saleStream() {
    return RandomSale.streamOf(10000);
  }

  public static void main(String[] args) {
    // how many sales?
    long saleCount = saleStream().count();
    System.out.println("Count of sales: " + saleCount);

    // any sales over $100?
    Supplier<DoubleStream> totalStream = () ->
            saleStream().mapToDouble(Sale::total);
    boolean bigSaleDay = totalStream.get().anyMatch(total -> total > 100.00);
    System.out.println("Big sale day? " + bigSaleDay);

    // maximum sale amount?
    DoubleSummaryStatistics stats =
            totalStream.get().summaryStatistics();
    System.out.println("Max sale amount: " + stats.getMax());
    System.out.println("Stats on total: " + stats);

    // how many items were sold today?
    Supplier<Stream<Item>> itemStream = () ->
            saleStream().flatMap(sale -> sale.items.stream());
    long itemCount = itemStream.get().count();
    System.out.println("Count of items: " + itemCount);

    // which different items were sold today?
    String uniqueItems = itemStream.get()
            .map(item -> item.identity)
            .distinct()
            .collect(Collectors.joining(" & "));
    System.out.println("Distinct items: " + uniqueItems);

    // summarize sales by store
    ConcurrentMap<String, DoubleSummaryStatistics> summary =
            saleStream().parallel()
                    .collect(
                 Collectors.groupingByConcurrent(sale -> Thread.currentThread().getName(),
                 Collectors.summarizingDouble(Sale::total)));
    System.out.println("Summary by thread: " + summary);
    summary.keySet().stream().sorted().forEach(store ->
            System.out.println(store + " stats: " + summary.get(store)));

  }







}
