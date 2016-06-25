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
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class Sources extends Base {

    ArrayList<Integer> content(int size) {
        return IntStream.range(0, size)
                .boxed().collect(toCollection(ArrayList::new));
    }

    // The content covered by the stream source
    Collection<Integer> content = content(1 << 20);

    /**
     * An ArrayList has good splitting characteristics.
     */
    @Test
    public void ArrayList() {
        graph(new ArrayList<>(content));
    }

    /**
     * A HashSet has reasonable splitting characteristics if there is
     * good distribution of keys.
     */
    @Test
    public void HashSet() {
        graph(new HashSet<>(content));
    }

    /**
     * A LinkedList has poor splitting characteristics.
     * <p>
     * A LinkedList is split by sequentially copying a prefix of elements
     * from the list into an array.  The size of the array arithmetically
     * increases per split.
     *
     * No parallel improvements will be obtained if the cost of copying the
     * elements greater than the cost of processing them.
     */
    @Test
    public void LinkedList() {
        graph(new LinkedList<>(content));
    }

    /**
     * An Iterator has poor splitting characteristics.
     */
    @Test
    public void Iterator_knownSize() {
        graph(StreamUtils.from(content.iterator(), content.size()));
    }

    @Test
    public void Iterator_unknownSize() {
        graph(StreamUtils.from(content.iterator()));
    }

    @Test
    public void Iterator_knownSize_small() {
        graph(StreamUtils.from(content(1 << 12).iterator(), 1 << 12));
    }

    @Test
    public void Iterator_unknownSize_small() {
        graph(StreamUtils.from(content(1 << 12).iterator()));
    }

    /**
     * The Files.lines implementation currently has poor splitting
     * characteristics.
     */
    @Test
    public void FileLines() throws IOException {
        long lines = graph(Files.lines(Paths.get("Ulysses.txt")),
                           s -> s,
                           Stream::count);
        System.out.println(lines);
    }

    /**
     * IntStream.range has good splitting characteristics.
     */
    @Test
    public void Range() {
        graph(IntStream.range(0, 1 << 20),
              s -> s,
              s -> s.reduce(0, Integer::sum));
    }

    @Test
    public void Generated() {
        graph(Stream.generate(() -> 1).limit(999));
    }
}
