package com.jessitron.fp4j.m2.timing;

import com.google.common.base.Function;
import org.apache.logging.log4j.Logger;

public class LoggerFunctions {

  public static Function<String, Void> info(final Logger logger) {
    return new Function<String, Void>() {
      @Override
      public Void apply(String s) {
        logger.info(s);
        return null;
      }
    };
  }
}
