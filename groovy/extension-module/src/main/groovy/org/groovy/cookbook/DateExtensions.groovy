package org.groovy.cookbook

import static java.util.Calendar.*
import java.text.DateFormatSymbols

class DateExtensions {

  static String getMonthName(Date date) {
    def dfs = new DateFormatSymbols()
    def months = dfs.getMonths()
    months[date[MONTH]]
  }

}