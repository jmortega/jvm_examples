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

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class Intermediates extends Base {

    ArrayList<Integer> content(int size) {
        return IntStream.range(0, size)
                .boxed().collect(toCollection(ArrayList::new));
    }

    @Test
    public void filterEven() {
        graph(content(1 << 20).stream(),
              s -> s.filter(i -> i % 2 == 0),
              s -> s.reduce(0, Integer::sum));
    }

    @Test
    public void filterBelow100() {
        graph(content(1 << 20).stream(),
              s -> s.filter(i -> i < 100),
              s -> s.reduce(0, Integer::sum));
    }

    @Test
    public void flatMap() {
        graph(content(1 << 10).stream(),
              s -> s.flatMap(i -> IntStream.range(0, i * 8).boxed()),
              s -> s.reduce(0, Integer::sum));
    }


    // limit

    @Test
    public void limit_ordered_knownSize() {
        graph(content(1 << 20).stream(),
              s -> s.limit(1 << 6),
              s -> s.reduce(0, Integer::sum));
    }

    @Test
    public void limit_unordered() {
        graph(new HashSet<>(content(1 << 20)).stream(),
              s -> s.limit(1 << 6),
              s -> s.reduce(0, Integer::sum));
    }

    @Test
    public void limit_ordered_unknownSize() throws IOException {
        graph(Files.lines(Paths.get("Ulysses.txt")),
              // Full barrier, parallel execution separated into
              // two stages
              s -> s.limit(1 << 12),
              Stream::count);
    }

    @Test
    public void limit_as_source_ordered_unknownSize() throws IOException {
        graph(Files.lines(Paths.get("Ulysses.txt")).limit(1 << 12),
              s -> s,
              Stream::count);
    }
}
