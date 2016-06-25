package com.jessitron.fp4j.m2.v_isolation;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class TimingTest {
    @Test
    public void testTimed() throws Exception {

        final String description = "Supply a carrot";
        final Supplier<String> carrotSupplier = () -> "Carrot";
        final AtomicReference<String> recordedOutput = new AtomicReference<>();

        final String result = Timing.timed(description,
                recordedOutput::set,
                carrotSupplier);

        assert(recordedOutput.get().contains(description));
    }

}
