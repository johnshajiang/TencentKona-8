/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
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

/*
 * @test
 * @run testng SimpleContinuation
 * @summary Basic test for continuation, test create/run/yield/resume/stop
 */

import static org.testng.Assert.*;

public class SimpleContinuation {
    static long count = 0;
    static ContinuationScope scope = new ContinuationScope("test");
    public static void main(String args[]) throws Exception {
        foo();
        System.out.println("finish first");
        assertEquals(count, 2);
        foo();
        System.out.println("finish second");
        assertEquals(count, 4);
    }

    static void foo() {
        Runnable target = new Runnable() {
            public void run() {
                System.out.println("before yield");
                count++;
                Continuation.yield(scope);
                System.out.println("resume yield");
                count++;
            }
        };
        Continuation cont = new Continuation(scope, target);
        cont.run();
        cont.run();
    }
}