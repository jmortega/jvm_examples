/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package oracle.stream.javaone.benchmarks;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/*

 java -Xmx1g -XX:MaxInlineLevel=12 -XX:-TieredCompilation -jar target/microbenchmarks.jar -gc true -f 1 -wi 5 -i 5 ".*PrimitiveAndBoxedShuf.*"

 java -Xmx1g -XX:-TieredCompilation -XX:MaxInlineLevel=12 -Dbenchmark.n=1000000 -jar target/microbenchmarks.jar -gc true -f 2 -rf csv -rff PrimitiveAndBoxedShuffled.gc.100000.txt ".*PrimitiveAndBoxedShuf.*"

 */

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class PrimitiveAndBoxed {

    @Param("100000")
    private int N;

    public int[] primitiveArray;
    public Integer[] boxedArray;

    @Setup
    public void setUp() {
        primitiveArray = new int[N];
        boxedArray = new Integer[N];
        for (int i = 0; i < N; i++) {
            primitiveArray[i] = 3 * i;
            boxedArray[i] = 3 * i;
        }
    }

    @Benchmark
    public int primitive_Parallel() {
        return Arrays.stream(primitiveArray).parallel()
                .map(e -> e * 5).sum();
    }

    @Benchmark
    public int primitive_Sequential() {
        return Arrays.stream(primitiveArray)
                .map(e -> e * 5).sum();
    }


    @Benchmark
    public int boxed_Parallel() {
        return Arrays.stream(boxedArray).parallel()
                .mapToInt(e -> e.intValue() * 5).sum();
    }

    @Benchmark
    public int boxed_Sequential() {
        return Arrays.stream(boxedArray)
                .mapToInt(e -> e.intValue() * 5).sum();
    }
}
