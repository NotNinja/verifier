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

import java.math.BigInteger;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.type.base.BaseComparableVerifierTestCase;
import io.skelp.verifier.type.base.BaseNumberVerifierTestCase;
import io.skelp.verifier.type.base.BaseTruthVerifierTestCase;

/**
 * Tests for the {@link BigIntegerVerifier} class.
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class BigIntegerVerifierTest {

    public static class BigIntegerVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<BigInteger, BigIntegerVerifier> {

        @Override
        protected BigIntegerVerifier createCustomVerifier() {
            return new BigIntegerVerifier(getMockVerification());
        }

        @Override
        protected BigInteger createValueOne() {
            return new BigInteger("123");
        }

        @Override
        protected BigInteger createValueTwo() {
            return new BigInteger("321");
        }

        @Override
        protected Class<?> getParentClass() {
            return Number.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return BigInteger.class;
        }
    }

    public static class BigIntegerVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<BigInteger, BigIntegerVerifier> {

        @Override
        protected BigIntegerVerifier createCustomVerifier() {
            return new BigIntegerVerifier(getMockVerification());
        }

        @Override
        public BigInteger getBaseValue() {
            return new BigInteger("50");
        }

        @Override
        public BigInteger getHigherValue() {
            return new BigInteger("75");
        }

        @Override
        public BigInteger getHighestValue() {
            return new BigInteger("100");
        }

        @Override
        public BigInteger getLowerValue() {
            return new BigInteger("25");
        }

        @Override
        public BigInteger getLowestValue() {
            return BigInteger.ZERO;
        }
    }

    public static class BigIntegerVerifierBaseNumberVerifierTest extends BaseNumberVerifierTestCase<BigInteger, BigIntegerVerifier> {

        @Override
        protected BigIntegerVerifier createCustomVerifier() {
            return new BigIntegerVerifier(getMockVerification());
        }

        @Override
        public BigInteger getEvenValue() {
            return new BigInteger("2");
        }

        @Override
        public BigInteger getOddValue() {
            return BigInteger.ONE;
        }

        @Override
        public BigInteger getPositiveOneValue() {
            return BigInteger.ONE;
        }

        @Override
        public BigInteger getNegativeOneValue() {
            return BigInteger.ONE.negate();
        }

        @Override
        public BigInteger getZeroValue() {
            return BigInteger.ZERO;
        }
    }

    public static class BigIntegerVerifierBaseTruthVerifierTest extends BaseTruthVerifierTestCase<BigInteger, BigIntegerVerifier> {

        @Override
        protected BigIntegerVerifier createCustomVerifier() {
            return new BigIntegerVerifier(getMockVerification());
        }

        @Override
        protected BigInteger[] getFalsehoodValues() {
            return new BigInteger[]{BigInteger.ZERO};
        }

        @Override
        protected BigInteger[] getTruthValues() {
            return new BigInteger[]{BigInteger.ONE};
        }
    }
}
