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
package oracle.stream.devoxx.benchmarks;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/*

 java -XX:-TieredCompilation  -jar target/microbenchmarks.jar -wi 5 -w 50ms -r 50ms -i 20 -f 1 ".*GrepFiles.*"


 java -Xmx1g -XX:-TieredCompilation -jar target/microbenchmarks.jar -f 2 -rf csv -rff GrepFiles.csv ".*GrepFiles.*"

 */

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class GrepFiles {

    private static String DIR = System.getProperty("benchmark.dir", "/Users/sandoz/Projects/jdk9/dev/jdk/src");

    private static String PATTEN = System.getProperty("benchmark.pattern", "MethodHandle");

    Path dir;

    Pattern pattern;

    @Setup
    public void setUp() {
        dir = Paths.get(DIR);
        pattern = Pattern.compile(PATTEN);
    }

    @GenerateMicroBenchmark
    public Map<Path, List<String>> sequential() throws Exception {
        Path[] paths;
        try (Stream<Path> files = Files.walk(dir)) {
            paths = files
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .toArray(Path[]::new);
        }

        Map<Path, List<String>> matches = new HashMap<>();
        Stream.of(paths)
                .forEach(p -> {
                    try (Stream<String> lines = lines(p)) {
                        List<String> lineMatches = lines.filter(pattern.asPredicate())
                                .collect(toList());
                        if (!lineMatches.isEmpty()) {
                            matches.put(p, lineMatches);
                        }
                    }
                });

        return matches;
    }

    @GenerateMicroBenchmark
    public Map<Path, List<String>> parallelPathAndLines() throws Exception {
        Path[] paths;
        try (Stream<Path> files = Files.walk(dir)) {
            paths = files
                    .parallel()
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .toArray(Path[]::new);
        }

        ConcurrentMap<Path, List<String>> matches = new ConcurrentHashMap<>();
        Stream.of(paths)
                .parallel()
                .forEach(p -> {
                    try (Stream<String> lines = lines(p)) {
                        List<String> lineMatches = lines.filter(pattern.asPredicate())
                                .collect(toList());
                        if (!lineMatches.isEmpty()) {
                            matches.put(p, lineMatches);
                        }
                    }
                });

        return matches;
    }

    @GenerateMicroBenchmark
    public Map<Path, List<String>> parallelPath() throws Exception {
        Path[] paths;
        try (Stream<Path> files = Files.walk(dir)) {
            paths = files
                    .parallel()
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .toArray(Path[]::new);
        }

        ConcurrentMap<Path, List<String>> matches = new ConcurrentHashMap<>();
        Stream.of(paths)
//                .parallel()
                .forEach(p -> {
                    try (Stream<String> lines = lines(p)) {
                        List<String> lineMatches = lines.filter(pattern.asPredicate())
                                .collect(toList());
                        if (!lineMatches.isEmpty()) {
                            matches.put(p, lineMatches);
                        }
                    }
                });

        return matches;
    }

    @GenerateMicroBenchmark
    public Map<Path, List<String>> parallelLines() throws Exception {
        Path[] paths;
        try (Stream<Path> files = Files.walk(dir)) {
            paths = files
//                    .parallel()
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .toArray(Path[]::new);
        }

        ConcurrentMap<Path, List<String>> matches = new ConcurrentHashMap<>();
        Stream.of(paths)
                .parallel()
                .forEach(p -> {
                    try (Stream<String> lines = lines(p)) {
                        List<String> lineMatches = lines.filter(pattern.asPredicate())
                                .collect(toList());
                        if (!lineMatches.isEmpty()) {
                            matches.put(p, lineMatches);
                        }
                    }
                });

        return matches;
    }

    static Stream<String> lines(Path p) {
        try {
            return Files.lines(p);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
