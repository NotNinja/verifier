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

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.type.base.BaseComparableVerifierTestCase;
import io.skelp.verifier.type.base.BaseNumberVerifierTestCase;
import io.skelp.verifier.type.base.BaseTruthVerifierTestCase;

/**
 * <p>
 * Tests for the {@link LongVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class LongVerifierTest {

    public static class LongVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Long, LongVerifier> {

        @Override
        protected LongVerifier createCustomVerifier() {
            return new LongVerifier(getMockVerification());
        }

        @Override
        protected Long createValueOne() {
            return new Long(123);
        }

        @Override
        protected Long createValueTwo() {
            return new Long(321);
        }

        @Override
        protected Class<?> getParentClass() {
            return Number.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return Long.class;
        }
    }

    public static class LongVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<Long, LongVerifier> {

        @Override
        protected LongVerifier createCustomVerifier() {
            return new LongVerifier(getMockVerification());
        }

        @Override
        public Long getBaseValue() {
            return 50L;
        }

        @Override
        public Long getHigherValue() {
            return 75L;
        }

        @Override
        public Long getHighestValue() {
            return 100L;
        }

        @Override
        public Long getLowerValue() {
            return 25L;
        }

        @Override
        public Long getLowestValue() {
            return 0L;
        }
    }

    public static class LongVerifierBaseNumberVerifierTest extends BaseNumberVerifierTestCase<Long, LongVerifier> {

        @Override
        protected LongVerifier createCustomVerifier() {
            return new LongVerifier(getMockVerification());
        }

        @Override
        public Long getEvenValue() {
            return 2L;
        }

        @Override
        public Long getOddValue() {
            return 1L;
        }

        @Override
        public Long getPositiveOneValue() {
            return 1L;
        }

        @Override
        public Long getNegativeOneValue() {
            return -1L;
        }

        @Override
        public Long getZeroValue() {
            return 0L;
        }
    }

    public static class LongVerifierBaseTruthVerifierTest extends BaseTruthVerifierTestCase<Long, LongVerifier> {

        @Override
        protected LongVerifier createCustomVerifier() {
            return new LongVerifier(getMockVerification());
        }

        @Override
        protected Long[] getFalsehoodValues() {
            return new Long[]{0L};
        }

        @Override
        protected Long[] getTruthValues() {
            return new Long[]{1L};
        }
    }
}
