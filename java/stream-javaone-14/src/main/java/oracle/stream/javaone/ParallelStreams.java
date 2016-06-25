/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package oracle.stream.javaone;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

public class ParallelStreams {

    public static class UnderTheHood {

        public static void main(String[] args) {
            Map<String, List<Integer>> m = IntStream.range(0, 128)
                    .parallel()
                    .boxed()
                    .collect(groupingBy(i -> Thread.currentThread().getName()));

            m.forEach((k, v) -> {
                System.out.printf("%s -> {size=%s} %s\n", k, v.size(), v);
            });
        }

        {
/*
                                     +-+
                  +------------------| |--------------------+
                  |                  +-+                    |
                  |                                         |
                  v                                         v
                 +-+                                       +-+
        +--------| |---------+                    +--------| |---------+
        |        +-+         |                    |        +-+         |
        |                    |                    |                    |
        v                    v                    v                    v
[ 0,  1,  2,  3]     [ 4,  5,  6,  7]     [ 8,  9, 10, 11]     [12, 13, 14, 15]

       tid1                 tid2                tid2                 tid1


                                     +-+
                  +------------------| |--------------------+
                  |                  +-+                    |
                  |                                         |
                  v                                         v
                 +-+                                       +-+
        +--------| |---------+                    +--------| |---------+
        |        +-+         |                    |        +-+         |
        |                    |                    |                    |
        v                    v                    v                    v
     "tid1" ->             "tid2" ->            "tid2" ->            "tid1" ->
[ 0,  1,  2,  3]     [ 4,  5,  6,  7]     [ 8,  9, 10, 11]     [12, 13, 14, 15]



                                     +-+
                  +------------------| |--------------------+
                  |                  +-+                    |
                  |                                         |
                  v                                         v
      "tid1" -> [ 0,  1,  2,  3]                "tid2" -> [ 8,  9, 10, 11]
      "tid2" -> [ 4,  5,  6,  7]                "tid1" -> [12, 13, 14, 15]



                    "tid1" -> [ 0,  1,  2,  3, 12, 13, 14, 15]
                    "tid2" -> [ 4,  5,  6,  7,  8,  9, 10, 11]

*/
        }
    }


    public static class LimitParallelism {

        public static void main(String[] args) {
            // DISABLE !
            //
            System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "0");

            Map<String, List<Integer>> m = IntStream.range(0, 128)
                    .parallel()
                    .boxed()
                    .collect(groupingBy(i -> Thread.currentThread().getName()));

            System.out.println(m.getClass());
            m.forEach((k, v) -> {
                System.out.printf("%s -> %s\n", k, v);
            });
        }
    }

    public static class NoCommomPoolForMe {

        public static void main(String[] args) {
            // Limited parallelism
            ForkJoinPool forkJoinPool = new ForkJoinPool(2);

            // Sneaky hack!
            // Submit on another pool
            forkJoinPool.submit(() -> {
                Map<String, List<Integer>> m = IntStream.range(0, 128)
                        .parallel()
                        .boxed()
                        .collect(groupingBy(i -> Thread.currentThread().getName()));

                m.forEach((k, v) -> {
                    System.out.printf("%s -> %s\n", k, v);
                });
            }).join();
        }
    }

    // Copied from jmh code base, see that for more details on how
    // it works
    private static volatile long consumedCPU = System.nanoTime();
    public static void consumeCPU(long tokens) {
        long t = consumedCPU;

        for (long i = tokens; i > 0; i--) {
            t += (t * 0x5DEECE66DL + 0xBL + i) & (0xFFFFFFFFFFFFL);
        }

        if (t == 42) {
            consumedCPU += t;
        }
    }
}
