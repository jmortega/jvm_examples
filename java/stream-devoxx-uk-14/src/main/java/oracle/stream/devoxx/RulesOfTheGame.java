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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class RulesOfTheGame {

    public static class Interference {
        public static void main(String[] args) {
            List<String> ws = Basics.words();
            ws.stream()
                    .filter(w -> w.length() > 4)
                    .forEach(w -> ws.add(w));
        }
    }

    public static class State {
        public static void main(String[] args) {
//            List<String> fws = Collections.synchronizedList(
//                    new ArrayList<>());
            List<String> fws = Basics.words().stream()
                    .parallel()
//                    .filter(w -> w.length() > 4)
                    .collect(toList());
            System.out.printf("%s\n", fws.size());
        }
    }

    public static class State_Change {
        public static void main(String[] args) {
            List<String> ws = Basics.words();

            Set<String> seen = Collections.synchronizedSet(new LinkedHashSet<>());
            List<String> dws = ws.stream()
                    .parallel()
                    .distinct()
                    .collect(toList());

            System.out.printf("%s %s %s\n", seen.size(), dws.size(), ws.size());
            System.out.println(seen);
            System.out.println(dws);
        }
    }
}
