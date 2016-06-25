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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

class CTree {
    // Map holding edges
    ConcurrentHashMap<CNode, CNode> edges = new ConcurrentHashMap<>();

    // Thread locals to leafs to count elements
    ThreadLocal<CNode> leafNodes = new ThreadLocal<>();

    // Add an edge to the graph
    void addEdge(CNode child, CNode parent) {
        edges.put(child, parent);
    }

    // Find a node given its name
    CNode find(String name) {
        return edges.values().stream().
                filter(cn -> cn.name.equals(name)).
                findFirst().get();
    }

    // Count outgoing elements
    void count() {
        CNode n = leafNodes.get();
        if (n != null) {
            n.outCount++;
        }
    }

    // Ascertain if the node is a leaf
    boolean isLeaf(CNode n) {
        return !edges.values().contains(n);
    }


    // Dot file methods

    void writeNode(CNode n, long time, PrintWriter w) {
        Map<String, String> attrs = new HashMap<>();

        String size;
        attrs.put("shape", "box");
        if (n.isSized()) {
            size = Long.toString(n.estimatedSize);

            attrs.put("style", "filled");
            if (n.isSubSized()) {
                attrs.put("color", "darkgrey");
            }
            else {
                attrs.put("color", "lightgrey");
            }
        }
        else {
            size = (n.estimatedSize == Long.MAX_VALUE)
                   ? "?"
                   : "~" + Long.toString(n.estimatedSize);
        }

        Map<String, String> label = new LinkedHashMap<>();
        label.put("N", size);
        if (n.isProcessed()) {
            label.put("tid", Long.toString(n.threadId));
            label.put("t", Long.toString(TimeUnit.NANOSECONDS.toMicros(time)));
        }

        attrs.put("label", quote(writeAttributes(label)));

        writeNode(n.name, attrs, w);
    }

    void writeNode(String name, Map<String, String> attrs, PrintWriter w) {
        w.print(quote(name));
        w.println(" [");

        writeAttributes(attrs, w);

        w.println("];");
    }

    void writeAttributes(Map<String, String> m, PrintWriter w) {
        boolean first = true;
        for (Map.Entry<String, String> e : m.entrySet()) {
            if (!first)
                w.println();
            w.format("%s = %s", e.getKey(), e.getValue());
            first = false;
        }
    }

    String writeAttributes(Map<String, String> m) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        writeAttributes(m, pw);
        pw.flush();
        return sw.toString();
    }

    void writeLeafNode(CNode n, PrintWriter pw) {
        long duration = TimeUnit.NANOSECONDS.toMicros(
                n.traversedTimeStamp - n.timeStamp);

        Map<String, String> label = new LinkedHashMap<>();
        label.put("in", Long.toString(n.inCount));
        if (n.outCount > 0) {
            label.put("out", Long.toString(n.outCount));
        }
        if (n.traversedTimeStamp > 0) {
            label.put("d", Long.toString(duration));
            if (n.inCount > 0)
                label.put("Q", String.format("%.4f", (double) duration / n.inCount));
        }
        Map<String, String> attrs = new HashMap<>();
        attrs.put("label", quote(writeAttributes(label)));

        writeNode(n.name + ".leaf", attrs, pw);
    }

    String quote(String s) {
        return "\"" + s + "\"";
    }

    void writeEdge(CNode c, CNode p, PrintWriter w) {
        writeEdge(p.name, c.name, w);
    }

    void writeLeafEdge(CNode c, PrintWriter w) {
        writeEdge(c.name, c.name + ".leaf", w);
    }

    void writeEdge(String from, String to, PrintWriter w) {
        w.format("%s -> %s\n", quote(from), quote(to));
    }

    void toDot(String graphName, PrintWriter w) {
        w.println(String.format("digraph %s {", quote(graphName)));

        // Root node
        CNode root = find("t");

        // Order non-root nodes
        List<CNode> children = edges.keySet().stream().
                sorted(Comparator.comparing(c -> c.name)).
                collect(toList());

        // Obtain processed leaf nodes
        List<CNode> leafs = children.stream().
                filter(this::isLeaf).
                filter(CNode::isProcessed).
                collect(toList());

        // Write root node
        writeNode(root, 0, w);

        // Write non-root nodes
        children.forEach(c -> {
            writeNode(c, c.timeStamp - root.timeStamp, w);
        });

        // Write processed leaf nodes
        leafs.forEach(c -> {
            writeLeafNode(c, w);
        });

        // Write edges
        children.forEach(c -> {
            CNode p = edges.get(c);
            writeEdge(c, p, w);
        });

        leafs.forEach(c -> {
            writeLeafEdge(c, w);
        });

        w.println("}");
    }
}

// A node in the computation tree
class CNode {
    // The name
    final String name;
    // The estimate size of elements covered by the associated spliterator
    final long estimatedSize;
    // The characteristics of the associated spliterator
    final int characteristics;
    // The thread-id that the associated spliterator was operated on
    long threadId;

    // The time-stamp when the associated spliterator was operated on
    // (split or first traversed)
    long timeStamp;

    // The time-stamp when traversal of an associated (leaf) spliterator
    // was completed
    long traversedTimeStamp;

    // The count of incoming elements from the stream source stage
    long inCount;
    // The count of outgoing elements to the stream terminal stage
    long outCount = 0;

    CNode(Spliterator<?> s, String name) {
        this.name = name;

        this.estimatedSize = s.estimateSize();
        this.characteristics = s.characteristics();
    }

    boolean isSized() {
        return (characteristics & Spliterator.SIZED) != 0;
    }

    boolean isSubSized() {
        return (characteristics & Spliterator.SUBSIZED) != 0;
    }

    boolean isProcessed() {
        return threadId != 0;
    }

    public String toString() {
        return name + " (" + estimatedSize + ")";
    }
}

// A graphing spliterator, that proxies a spliterator and tracks its
// operations
abstract class GraphingSpliterator<T, T_CONS, T_SP extends Spliterator<T>> implements Spliterator<T> {
    final CTree ct;
    CNode n;
    final T_SP s;
    boolean tryAdvanced;

    protected GraphingSpliterator(CTree ct, CNode n, T_SP s) {
        this.ct = ct;
        this.n = n;
        this.s = s;
    }

    @Override
    public long estimateSize() {
        return s.estimateSize();
    }

    @Override
    public long getExactSizeIfKnown() {
        return s.getExactSizeIfKnown();
    }

    @Override
    public int characteristics() {
        return s.characteristics();
    }

    @Override
    public boolean hasCharacteristics(int characteristics) {
        return s.hasCharacteristics(characteristics);
    }

    @Override
    public Comparator<T> getComparator() {
        return (Comparator<T>) s.getComparator();
    }

    public boolean tryAdvance(T_CONS action) {
        if (!tryAdvanced) {
            n.threadId = Thread.currentThread().getId();
            n.timeStamp = System.nanoTime();
            ct.leafNodes.set(n);
            tryAdvanced = true;
        }

        if (_tryAdvance(action)) {
            n.inCount++;
            return true;
        }
        else {
            ct.leafNodes.set(null);
            n.traversedTimeStamp = System.nanoTime();
            return false;
        }
    }

    abstract boolean _tryAdvance(T_CONS action);

    public void forEachRemaining(T_CONS action) {
        n.threadId = Thread.currentThread().getId();
        n.timeStamp = System.nanoTime();
        ct.leafNodes.set(n);

        _forEachRemaining(action);

        ct.leafNodes.set(null);
        n.traversedTimeStamp = System.nanoTime();
    }

    abstract void _forEachRemaining(T_CONS action);

    @Override
    public T_SP trySplit() {
        n.threadId = Thread.currentThread().getId();
        n.timeStamp = System.nanoTime();

        T_SP child = (T_SP) s.trySplit();
        if (child == null)
            return null;

        // Parent node
        CNode pn = n;

        // Right node, update in place
        n = new CNode(s, pn.name + ".r");
        ct.addEdge(n, pn);

        // Left node
        CNode ln = new CNode(child, pn.name + ".l");
        ct.addEdge(ln, pn);

        return makeSpliterator(ct, ln, child);
    }

    abstract T_SP makeSpliterator(CTree ct, CNode n, T_SP s);

    static final class OfRef<T> extends GraphingSpliterator<T, Consumer<? super T>, Spliterator<T>> {

        OfRef(CTree ct, CNode n, Spliterator<T> s) {
            super(ct, n, s);
        }

        @Override
        boolean _tryAdvance(Consumer<? super T> action) {
            return s.tryAdvance(action);
        }

        @Override
        void _forEachRemaining(Consumer<? super T> action) {
            s.forEachRemaining(e -> {
                n.inCount++;
                action.accept(e);
            });
        }

        @Override
        OfRef<T> makeSpliterator(CTree ct, CNode n, Spliterator<T> s) {
            return new OfRef<>(ct, n, s);
        }
    }

    static final class OfInt extends GraphingSpliterator<Integer, IntConsumer, Spliterator.OfInt> implements Spliterator.OfInt {

        OfInt(CTree ct, CNode n, Spliterator.OfInt s) {
            super(ct, n, s);
        }

        @Override
        boolean _tryAdvance(IntConsumer action) {
            return s.tryAdvance(action);
        }

        @Override
        void _forEachRemaining(IntConsumer action) {
            s.forEachRemaining((int e) -> {
                n.inCount++;
                action.accept(e);
            });
        }

        @Override
        GraphingSpliterator.OfInt makeSpliterator(CTree ct, CNode n, Spliterator.OfInt s) {
            return new GraphingSpliterator.OfInt(ct, n, s);
        }
    }

    static final class OfLong extends GraphingSpliterator<Long, LongConsumer, Spliterator.OfLong> implements Spliterator.OfLong {

        OfLong(CTree ct, CNode n, Spliterator.OfLong s) {
            super(ct, n, s);
        }

        @Override
        boolean _tryAdvance(LongConsumer action) {
            return s.tryAdvance(action);
        }

        @Override
        void _forEachRemaining(LongConsumer action) {
            s.forEachRemaining((long e) -> {
                n.inCount++;
                action.accept(e);
            });
        }

        @Override
        GraphingSpliterator.OfLong makeSpliterator(CTree ct, CNode n, Spliterator.OfLong s) {
            return new GraphingSpliterator.OfLong(ct, n, s);
        }
    }

    static final class OfDouble extends GraphingSpliterator<Double, DoubleConsumer, Spliterator.OfDouble> implements Spliterator.OfDouble {

        OfDouble(CTree ct, CNode n, Spliterator.OfDouble s) {
            super(ct, n, s);
        }

        @Override
        boolean _tryAdvance(DoubleConsumer action) {
            return s.tryAdvance(action);
        }

        @Override
        void _forEachRemaining(DoubleConsumer action) {
            s.forEachRemaining((double e) -> {
                n.inCount++;
                action.accept(e);
            });
        }

        @Override
        GraphingSpliterator.OfDouble makeSpliterator(CTree ct, CNode n, Spliterator.OfDouble s) {
            return new GraphingSpliterator.OfDouble(ct, n, s);
        }
    }

}

public final class StreamGrapher {

    private static <T> Stream<T> graphStream(CTree ct, Stream<T> s) {
        Spliterator<T> sp = s.spliterator();

        GraphingSpliterator.OfRef<T> gsp = new GraphingSpliterator.OfRef<>(ct, new CNode(sp, "t"), sp);

        return StreamSupport.stream(gsp, s.isParallel());
    }

    private static IntStream graphIntStream(CTree ct, IntStream s) {
        Spliterator.OfInt sp = s.spliterator();

        GraphingSpliterator.OfInt gsp = new GraphingSpliterator.OfInt(ct, new CNode(sp, "t"), sp);

        return StreamSupport.intStream(gsp, s.isParallel());
    }

    private static LongStream graphLongStream(CTree ct, LongStream s) {
        Spliterator.OfLong sp = s.spliterator();

        GraphingSpliterator.OfLong gsp = new GraphingSpliterator.OfLong(ct, new CNode(sp, "t"), sp);

        return StreamSupport.longStream(gsp, s.isParallel());
    }

    private static DoubleStream graphDoubleStream(CTree ct, DoubleStream s) {
        Spliterator.OfDouble sp = s.spliterator();

        GraphingSpliterator.OfDouble gsp = new GraphingSpliterator.OfDouble(ct, new CNode(sp, "t"), sp);

        return StreamSupport.doubleStream(gsp, s.isParallel());
    }

    public static <T, U, TS extends BaseStream<T, TS>, US extends BaseStream<U, US>, V> V eval(
            TS s,
            Function<TS, US> f,
            Function<US, V> t,
            String graphName,
            PrintWriter w) {
        CTree ct = new CTree();

        // Source stage
        s = s.parallel();
        if (s instanceof Stream) {
            s = (TS) graphStream(ct, (Stream) s);
        }
        else if (s instanceof IntStream) {
            s = (TS) graphIntStream(ct, (IntStream) s);
        }
        else if (s instanceof LongStream) {
            s = (TS) graphLongStream(ct, (LongStream) s);
        }
        else if (s instanceof DoubleStream) {
            s = (TS) graphDoubleStream(ct, (DoubleStream) s);
        }

        // Intermediate stage
        US us = f.apply(s.parallel());
        if (us instanceof Stream) {
            us = (US) ((Stream) us).peek(e -> ct.count());
        }
        else if (us instanceof IntStream) {
            us = (US) ((IntStream) us).peek(e -> ct.count());
        }
        else if (us instanceof LongStream) {
            us = (US) ((DoubleStream) us).peek(e -> ct.count());
        }
        else if (us instanceof DoubleStream) {
            us = (US) ((LongStream) us).peek(e -> ct.count());
        }

        // Terminal stage
        V v = t.apply(us);

        ct.toDot(graphName, w);
        w.flush();
        return v;
    }
}
