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

package oracle.stream.devoxx;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.lang.System.out;
import static java.util.stream.Collectors.*;


public class Basics {

    /*
     ____  _                                 _    ____ ___
    / ___|| |_ _ __ ___  __ _ _ __ ___      / \  |  _ \_ _|
    \___ \| __| '__/ _ \/ _` | '_ ` _ \    / _ \ | |_) | |
     ___) | |_| | |  __/ (_| | | | | | |  / ___ \|  __/| |
    |____/ \__|_|  \___|\__,_|_| |_| |_| /_/   \_\_|  |___|

    */

    static final Pattern WORDS = Pattern.compile("[\\s,\\.:;?-]");

    static String text() {
        return "To be, or not to be, that is the question:\n" +
               "Whether 'tis nobler in the mind to suffer\n" +
               "The slings and arrows of outrageous fortune,\n" +
               "Or to take arms against a sea of troubles,\n" +
               "And, by opposing end them?--To die,--to sleep,\n" +
               "No more;--and by a sleep, to say we end\n" +
               "The heart-ache, and the thousand natural shocks\n" +
               "That flesh is heir to: 'tis a consummation\n" +
               "Devoutly to be wished. To die,--to sleep,--\n" +
               "To sleep! perchance to dream: ay, there's the rub;\n" +
               "For in that sleep of death what dreams may come,\n" +
               "When we have shuffled off this mortal coil,\n" +
               "Must give us pause: There's the respect\n" +
               "That makes calamity of so long life;" +
               "For who would bear the whips and scorns of time,\n" +
               "The oppressor's wrong, the proud man's contumely,\n" +
               "The pangs of despised love, the law's delay," +
               "The insolence of office, and the spurns" +
               "That patient merit of the unworthy takes," +
               "When he himself might his quietus make" +
               "With a bare bodkin? Who would fardels bear,\n" +
               "To groan and sweat under a weary life,\n" +
               "But that the dread of something after death,\n" +
               "The undiscovered country, from whose bourn\n" +
               "No traveller returns, puzzles the will,\n" +
               "And makes us rather bear those ills we have\n" +
               "Than fly to others that we know not of?\n" +
               "Thus, conscience does make cowards of us all;\n" +
               "And thus the native hue of resolution\n" +
               "Is sicklied o'er with the pale cast of thought;\n" +
               "And enterprises of great pith and moment,\n" +
               "With this regard, their currents turn away,\n" +
               "And lose the name of action.--";

    }

    static List<String> words() {
        return WORDS.splitAsStream(text()).
                filter(s -> !s.isEmpty()).
                collect(toList());
    }

    public static class WordCountImperative {
        public static void main(String[] args) {

            long count = 0;
            for (String w : words()) {
                if (w.length() > 4) {
                    count++;
                }
            }

            out.println(count);
        }

        // Conflates what with how
        //    The filtering and counting are mixed with the iteration
        //    The iteration is sequential

    }

    public static class WordCount {
        public static void main(String[] args) {
            // Count all words

            {
              long count = words().stream()
                      .filter(w -> w.length() > 4)
                      .mapToLong(e -> 1L)
                      .reduce(0, Long::sum);
                out.println(count);
            }

            {
                Stream<String>         words = words().stream();
                Stream<String> filteredWords = words.filter(w -> w.length() > 4);
                LongStream              ones = filteredWords.mapToLong(w -> 1);
                long                   count = ones.reduce(0, Long::sum);
                out.println(count);
            }

        }

        // Stream is a lazy view of elements covered by a source
        // More what less how
        //   Lambdas (functions, preferably stateless) express the behaviour
        // Raised the level of abstraction
        //   If you play by the rules optimizations are possible
        //     Go parallel
        //     Execute on a GPU

    }

    public static class WordReduceAndCollect {
        public static void main(String[] args) {
            // Reduce all words into one String

            {
                // The way to exercise garbage collection
                Optional<String> combinedWords = words().stream()
                        .filter(w -> w.length() > 4)
                        .reduce((a, b) -> a + ", " + b);
                out.println(combinedWords.orElse(""));

            }

            {
                // The way better way using collect
                // Underneath the covers a StringBuilder will be used
                // No need to worry about a base value, associativity
                // or an empty stream
                String combinedWords = words().stream()
                        .filter(w -> w.length() > 4)
                        .collect(joining(", "));
                out.println(combinedWords);
            }
        }

        // Sequential reduce is like a fold-left
        //
        // (((("" + ", " + a) + ", " + b) + ", " + c) + ...)
        //
        // ((((a + ", " + b) + ", " + c) + ", " + d) + ...)
        //
        // Quite inefficient in Java
        //
        // WARNING: reduction function is associative
        // WARNING: the base value is not an initial value
        //          base + x = x
        //
        // Implementation is free to change grouping but not the order
        //
        // ((("" + a) + b) + (("" + c) + d) + ...)
        //
        // ((a + b) + (b + c) + ...)

    }


    // Reducing and collecting

    public static class WordCollectWithUlyssesAndGroupingBy {

        static final Path ULYSSES = Paths.get("Ulysses.txt");

        public static void main(String[] args) throws IOException {
            // Collect how many times a word occurs: Map<String, Long>

            try (Stream<String> lines = Files.lines(ULYSSES)) {
                Stream<String> words = lines.flatMap(WORDS::splitAsStream);

                Map<String, Long> wc = words.filter(w -> w.length() > 4)
                        .collect(groupingBy(i -> i,
                                            counting()));

                wc.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .forEach(e -> out.printf("%s -> %s\n",
                                                 e.getKey(), e.getValue()));
            }

        }
    }
}