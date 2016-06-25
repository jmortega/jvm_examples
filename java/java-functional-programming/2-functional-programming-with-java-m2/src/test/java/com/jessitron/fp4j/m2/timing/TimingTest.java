package com.jessitron.fp4j.m2.timing;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

public class TimingTest {
    @Test
    public void testTimed() throws Exception {
      final String description = "Supply carrot";
      AtomicReference<String> output = new AtomicReference<>("");

      Timing.timed(description,
              output::set,
              () -> "carrot");


      assert(output.get().contains(description));
    }
}
