package com.jessitron.fpwj.m3.contexts;

import com.jessitron.fpwj.m3.sales.*;
import com.jessitron.fpwj.m3.sales.random.RandomSale;

import java.util.Optional;
import java.util.stream.Stream;

public class OptionalExample {

  public static void main(String[] args) {
    System.out.println("Who bought a carrot? "
               + carrotCustomer().orElse("I can't say."));
  }

  static Stream<Sale> saleStream() {
    return RandomSale.streamOf(3);
  }

  static Optional<Sale> findSaleOf(String itemName) {
    return saleStream().filter(sale ->
                        sale.items.stream().anyMatch(item ->
                        item.identity.equals(itemName)))
                       .findFirst();
  }

  // Customer who bought a carrot
  static Optional<String> carrotCustomer() {
    return findSaleOf("carrot").flatMap(sale -> sale.customer);
  }

  // Store that sold a carrot
  static Optional<Store> carrotStore() {
    return findSaleOf("carrot").map(sale -> sale.store);
  }

}
