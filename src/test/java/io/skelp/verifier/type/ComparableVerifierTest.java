/*
 * Copyright (C) 2016 Alasdair Mercer, Skelp
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.skelp.verifier.type;

import io.skelp.verifier.type.base.BaseComparableVerifierTestBase;

/**
 * Tests for the {@link ComparableVerifier} class.
 *
 * @author Alasdair Mercer
 */
public class ComparableVerifierTest extends BaseComparableVerifierTestBase<Integer, ComparableVerifier<Integer>> {

    @Override
    protected ComparableVerifier<Integer> createCustomVerifier() {
        return new ComparableVerifier<>(getMockVerification());
    }

    @Override
    protected ComparableValues<Integer> getComparableValues() {
        return new ComparableValues<Integer>() {
            @Override
            public Integer getBase() {
                return 50;
            }

            @Override
            public Integer getHigher() {
                return 75;
            }

            @Override
            public Integer getHighest() {
                return 100;
            }

            @Override
            public Integer getLower() {
                return 25;
            }

            @Override
            public Integer getLowest() {
                return 0;
            }
        };
    }
}
