/*
 * Copyright (C) 2017 Alasdair Mercer, !ninja
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
package org.notninja.verifier.type;

import java.math.BigDecimal;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.notninja.verifier.AbstractCustomVerifierTestCase;
import org.notninja.verifier.type.base.BaseComparableVerifierTestCase;
import org.notninja.verifier.type.base.BaseNumberVerifierTestCase;
import org.notninja.verifier.type.base.BaseTruthVerifierTestCase;

/**
 * <p>
 * Tests for the {@link BigDecimalVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class BigDecimalVerifierTest {

    public static class BigDecimalVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<BigDecimal, BigDecimalVerifier> {

        @Override
        protected BigDecimalVerifier createCustomVerifier() {
            return new BigDecimalVerifier(getMockVerification());
        }

        @Override
        protected BigDecimal createValueOne() {
            return new BigDecimal(123);
        }

        @Override
        protected BigDecimal createValueTwo() {
            return new BigDecimal(321);
        }

        @Override
        protected Class<?> getParentClass() {
            return Number.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return BigDecimal.class;
        }
    }

    public static class BigDecimalVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<BigDecimal, BigDecimalVerifier> {

        @Override
        protected BigDecimalVerifier createCustomVerifier() {
            return new BigDecimalVerifier(getMockVerification());
        }

        @Override
        public BigDecimal getBaseValue() {
            return new BigDecimal(50);
        }

        @Override
        public BigDecimal getHigherValue() {
            return new BigDecimal(75);
        }

        @Override
        public BigDecimal getHighestValue() {
            return new BigDecimal(100);
        }

        @Override
        public BigDecimal getLowerValue() {
            return new BigDecimal(25);
        }

        @Override
        public BigDecimal getLowestValue() {
            return BigDecimal.ZERO;
        }
    }

    public static class BigDecimalVerifierBaseNumberVerifierTest extends BaseNumberVerifierTestCase<BigDecimal, BigDecimalVerifier> {

        @Override
        protected BigDecimalVerifier createCustomVerifier() {
            return new BigDecimalVerifier(getMockVerification());
        }

        @Override
        public BigDecimal getEvenValue() {
            return new BigDecimal(2);
        }

        @Override
        public BigDecimal getOddValue() {
            return BigDecimal.ONE;
        }

        @Override
        public BigDecimal getPositiveOneValue() {
            return BigDecimal.ONE;
        }

        @Override
        public BigDecimal getNegativeOneValue() {
            return BigDecimal.ONE.negate();
        }

        @Override
        public BigDecimal getZeroValue() {
            return BigDecimal.ZERO;
        }
    }

    public static class BigDecimalVerifierBaseTruthVerifierTest extends BaseTruthVerifierTestCase<BigDecimal, BigDecimalVerifier> {

        @Override
        protected BigDecimalVerifier createCustomVerifier() {
            return new BigDecimalVerifier(getMockVerification());
        }

        @Override
        protected BigDecimal[] getFalsyValues() {
            return new BigDecimal[]{BigDecimal.ZERO};
        }

        @Override
        protected BigDecimal[] getTruthyValues() {
            return new BigDecimal[]{BigDecimal.ONE};
        }
    }
}
