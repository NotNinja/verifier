/*
 * Copyright (C) 2017 Alasdair Mercer, Skelp
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
 * Tests for the {@link DoubleVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class DoubleVerifierTest {

    public static class DoubleVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Double, DoubleVerifier> {

        @Override
        protected DoubleVerifier createCustomVerifier() {
            return new DoubleVerifier(getMockVerification());
        }

        @Override
        protected Double createValueOne() {
            return new Double(123);
        }

        @Override
        protected Double createValueTwo() {
            return new Double(321);
        }

        @Override
        protected Class<?> getParentClass() {
            return Number.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return Double.class;
        }
    }

    public static class DoubleVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<Double, DoubleVerifier> {

        @Override
        protected DoubleVerifier createCustomVerifier() {
            return new DoubleVerifier(getMockVerification());
        }

        @Override
        public Double getBaseValue() {
            return 50D;
        }

        @Override
        public Double getHigherValue() {
            return 75D;
        }

        @Override
        public Double getHighestValue() {
            return 100D;
        }

        @Override
        public Double getLowerValue() {
            return 25D;
        }

        @Override
        public Double getLowestValue() {
            return 0D;
        }
    }

    public static class DoubleVerifierBaseNumberVerifierTest extends BaseNumberVerifierTestCase<Double, DoubleVerifier> {

        @Override
        protected DoubleVerifier createCustomVerifier() {
            return new DoubleVerifier(getMockVerification());
        }

        @Override
        public Double getEvenValue() {
            return 2D;
        }

        @Override
        public Double getOddValue() {
            return 1D;
        }

        @Override
        public Double getPositiveOneValue() {
            return 1D;
        }

        @Override
        public Double getNegativeOneValue() {
            return -1D;
        }

        @Override
        public Double getZeroValue() {
            return 0D;
        }
    }

    public static class DoubleVerifierBaseTruthVerifierTest extends BaseTruthVerifierTestCase<Double, DoubleVerifier> {

        @Override
        protected DoubleVerifier createCustomVerifier() {
            return new DoubleVerifier(getMockVerification());
        }

        @Override
        protected Double[] getFalsyValues() {
            return new Double[]{0D};
        }

        @Override
        protected Double[] getTruthyValues() {
            return new Double[]{1D};
        }
    }
}
