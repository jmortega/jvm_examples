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

import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.util.VMSupport;

import java.io.PrintWriter;

import static java.lang.System.out;

public class ArrayLayout {

    public static void main(String[] args) throws Exception {
        out.println(VMSupport.vmDetails());

        PrintWriter pw = new PrintWriter(System.out, true);

        // Create an array of Boxed integer
        Integer[] arr = new Integer[10];
        for (int i = 0; i < 10; i++) {
            arr[i] = new Integer(i);
        }

        // Print out initial layout and when it changes due to
        // garbage collection moving references around
        String last = null;
        for (int c = 0; c < 100; c++) {
            String current = GraphLayout.parseInstance(arr).toPrintable();

            if (last == null || !last.equalsIgnoreCase(current)) {
                pw.println(current);
                last = current;
            }

            // Create some stuff to garbage and perturb layout
            String[] a = new String[10000];
            for (int i = 0; i < a.length; i++) {
                a[i] = "" + i;
            }

            System.gc();
        }

        pw.close();
    }


}
