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

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

public abstract class Base {

    // Reduce size of computation tree for ease of display
    @BeforeClass
    public static void reduceParallelism() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");
    }

    // Inject the test name
    @Rule
    public TestName name = new TestName();

    private String baseName() {
        return this.getClass().getSimpleName() + "." + name.getMethodName();
    }

    Integer graph(Collection<Integer> c) {
        return graph(c.stream());
    }

    Integer graph(Stream<Integer> source) {
        return graph(source,
                     // Empty Intermediate stage
                     s -> s,
                     // Terminal stage processing all elements
                     // WARNING!: way too much boxing going on
                     s -> s.reduce(0, Integer::sum));
    }

    // Graph the stream to a dot and SVG file whose names are derived
    // from the test class name and test method name
    <T, U, TS extends BaseStream<T, TS>, US extends BaseStream<U, US>, V> V graph(
            TS source,
            Function<TS, US> f,
            Function<US, V> t) {
        String base = baseName();
        String dot = base + ".dot";
        String svg = base + ".svg";

        try {
            V v = StreamGrapher.eval(source,
                                     f,
                                     t,
                                     base,
                                     new PrintWriter(new FileOutputStream(dot)));

            Dot.dotToSvg(new FileInputStream(dot),
                         new FileOutputStream(svg));
            return v;
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // Open SVG
    @After
    public void openInBrowser() {
        String svg = baseName() + ".svg";
        Browser.openInBrowser(svg);
    }

}
