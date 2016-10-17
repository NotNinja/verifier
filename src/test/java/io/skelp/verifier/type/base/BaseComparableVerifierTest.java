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
package io.skelp.verifier.type.base;

import static org.junit.Assert.*;

import org.junit.Test;

import io.skelp.verifier.verification.Verification;

/**
 * Tests for the {@link BaseComparableVerifier} class.
 *
 * @author Alasdair Mercer
 */
public class BaseComparableVerifierTest extends BaseComparableVerifierTestBase<BaseComparableVerifierTest.ComparableWrapper, BaseComparableVerifierTest.BaseComparableVerifierTestImpl> {

    @Test
    public void hackCoverage() throws Exception {
        // TODO: Determine how to avoid this
        assertEquals(4, BaseComparableVerifierTestImpl.ComparisonOperator.values().length);
        assertEquals(BaseComparableVerifierTestImpl.ComparisonOperator.GREATER_THAN, BaseComparableVerifierTestImpl.ComparisonOperator.valueOf("GREATER_THAN"));
    }

    @Override
    protected BaseComparableVerifierTestImpl createCustomVerifier() {
        return new BaseComparableVerifierTestImpl(getMockVerification());
    }

    @Override
    protected ComparableValues<ComparableWrapper> getComparableValues() {
        return new ComparableValues<ComparableWrapper>() {
            @Override
            public ComparableWrapper getBase() {
                return new ComparableWrapper(50);
            }

            @Override
            public ComparableWrapper getHigher() {
                return new ComparableWrapper(75);
            }

            @Override
            public ComparableWrapper getHighest() {
                return new ComparableWrapper(100);
            }

            @Override
            public ComparableWrapper getLower() {
                return new ComparableWrapper(25);
            }

            @Override
            public ComparableWrapper getLowest() {
                return new ComparableWrapper(0);
            }
        };
    }

    static class BaseComparableVerifierTestImpl extends BaseComparableVerifier<ComparableWrapper, BaseComparableVerifierTestImpl> {

        BaseComparableVerifierTestImpl(Verification<ComparableWrapper> verification) {
            super(verification);
        }
    }

    static class ComparableWrapper implements Comparable<ComparableWrapper> {

        final Integer comparable;

        ComparableWrapper(Integer comparable) {
            this.comparable = comparable;
        }

        @Override
        public int compareTo(ComparableWrapper other) {
            return comparable.compareTo(other.comparable);
        }
    }
}
