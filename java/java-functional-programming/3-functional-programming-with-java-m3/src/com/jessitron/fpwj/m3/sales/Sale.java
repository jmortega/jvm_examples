package com.jessitron.fpwj.m3.sales;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Sale {

  final public Store store;
  final public Date date;
  final public Optional<String> customer;
  final public List<Item> items;

  public Sale(Store store, Date date, Optional<String> customer, List<Item> items) {
    this.store = store;
    this.date = date;
    this.customer = customer;
    this.items = items;
  }

  public double total() {
    return items.stream().mapToDouble(item -> item.price).sum();
  }
}
