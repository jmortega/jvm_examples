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
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

import static java.lang.System.out;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Basics {

    static String text() {
        return
                "The following is intended to outline our general product " +
                "direction. It is intended for information purposes only, " +
                "and may not be incorporated into any contract. It is not " +
                "a commitment to deliver any material, code, or functionality, " +
                "and should not be relied upon in making purchasing decisions. " +
                "The development, release, and timing of any features or " +
                "functionality described for Oracle’s products remains at " +
                "the sole discretion of Oracle.";
    }

    static List<String> words() {
        Pattern p = Pattern.compile("[ ]+");
        return p.splitAsStream(text()).collect(toList());
    }

    static List<String> words = words();

    public static void main(String[] args) {
    }

    static <T> void assertIdentityValue(T base, T a, BiFunction<T, T, T> f) {
        // Partial function that should be equivalent to the identity function
        // for values
        Function<T, T> identity = t -> f.apply(base, t);

        T r = identity.apply(a);
        assert a.equals(r);
    }

    static <T> void assertAssociativity(T a, T b, T c, BiFunction<T, T, T> f) {
        T r1 = f.apply(a, f.apply(b, c));
        T r2 = f.apply(f.apply(a, b), c);
        assert r1.equals(r2);
    }

    static void rot13Text() {
        String r = text().chars()
                .map(Basics::rot13Char)
                .collect(StringBuilder::new,
                         StringBuilder::appendCodePoint,
                         StringBuilder::append)
                .toString();
        out.println(r);
    }

    static int rot13Char(int c) {
        if (c >= 'a' && c <= 'm') {
            c += 13;
        }
        else if (c >= 'n' && c <= 'z') {
            c -= 13;
        }
        else if (c >= 'A' && c <= 'M') {
            c += 13;
        }
        else if (c >= 'N' && c <= 'Z') {
            c -= 13;
        }
        return c;
    }
}
