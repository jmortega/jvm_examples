package com.jessitron.fpwj.m3.sales.random;

import com.jessitron.fpwj.m3.sales.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jessitron.fpwj.m3.sales.random.RandomUtil.*;

public class RandomSale {


   public static Stream<Sale> streamOf(long qty) {
     return Stream.generate(supplier).limit(qty);
   }


  public static Supplier<Sale> supplier = () -> new Sale(
          pickAStore(),
          new Date(),
          pickACustomer(),
          randomListOfItems());





  private static Random random = new Random();

  private static final int MAX_ITEMS = 6;
  private static List<Item> randomListOfItems() {
    int howMany = random.nextInt(MAX_ITEMS - 1) + 1;
    return RandomItems.infiniteStream()
            .limit(howMany)
            .collect(Collectors.toList());
  }

  private static final Double PERCENT_NO_CUSTOMER = 0.25;
  private static final String[] CUSTOMERS =
          new String[] { "Wilma", "Betty", "Fred", "Barney", "Dino"};
  private static Optional<String> pickACustomer() {
    if (random.nextDouble() < PERCENT_NO_CUSTOMER) {
      return Optional.empty();
    } else {
      return Optional.of(randomElement(CUSTOMERS));
    }
  }


  private static Store pickAStore() {
    return randomElement(Store.values());
  }
}
