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
 * Tests for the {@link IntegerVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class IntegerVerifierTest {

    public static class IntegerVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Integer, IntegerVerifier> {

        @Override
        protected IntegerVerifier createCustomVerifier() {
            return new IntegerVerifier(getMockVerification());
        }

        @Override
        protected Integer createValueOne() {
            return new Integer(123);
        }

        @Override
        protected Integer createValueTwo() {
            return new Integer(321);
        }

        @Override
        protected Class<?> getParentClass() {
            return Number.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return Integer.class;
        }
    }

    public static class IntegerVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<Integer, IntegerVerifier> {

        @Override
        protected IntegerVerifier createCustomVerifier() {
            return new IntegerVerifier(getMockVerification());
        }

        @Override
        public Integer getBaseValue() {
            return 50;
        }

        @Override
        public Integer getHigherValue() {
            return 75;
        }

        @Override
        public Integer getHighestValue() {
            return 100;
        }

        @Override
        public Integer getLowerValue() {
            return 25;
        }

        @Override
        public Integer getLowestValue() {
            return 0;
        }
    }

    public static class IntegerVerifierBaseNumberVerifierTest extends BaseNumberVerifierTestCase<Integer, IntegerVerifier> {

        @Override
        protected IntegerVerifier createCustomVerifier() {
            return new IntegerVerifier(getMockVerification());
        }

        @Override
        public Integer getEvenValue() {
            return 2;
        }

        @Override
        public Integer getOddValue() {
            return 1;
        }

        @Override
        public Integer getPositiveOneValue() {
            return 1;
        }

        @Override
        public Integer getNegativeOneValue() {
            return -1;
        }

        @Override
        public Integer getZeroValue() {
            return 0;
        }
    }

    public static class IntegerVerifierBaseTruthVerifierTest extends BaseTruthVerifierTestCase<Integer, IntegerVerifier> {

        @Override
        protected IntegerVerifier createCustomVerifier() {
            return new IntegerVerifier(getMockVerification());
        }

        @Override
        protected Integer[] getFalsyValues() {
            return new Integer[]{0};
        }

        @Override
        protected Integer[] getTruthyValues() {
            return new Integer[]{1};
        }
    }
}
