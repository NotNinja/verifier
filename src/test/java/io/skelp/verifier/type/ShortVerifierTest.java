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
 * Tests for the {@link ShortVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class ShortVerifierTest {

    public static class ShortVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Short, ShortVerifier> {

        @Override
        protected ShortVerifier createCustomVerifier() {
            return new ShortVerifier(getMockVerification());
        }

        @Override
        protected Short createValueOne() {
            return new Short((short) 123);
        }

        @Override
        protected Short createValueTwo() {
            return new Short((short) 321);
        }

        @Override
        protected Class<?> getParentClass() {
            return Number.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return Short.class;
        }
    }

    public static class ShortVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<Short, ShortVerifier> {

        @Override
        protected ShortVerifier createCustomVerifier() {
            return new ShortVerifier(getMockVerification());
        }

        @Override
        public Short getBaseValue() {
            return 50;
        }

        @Override
        public Short getHigherValue() {
            return 75;
        }

        @Override
        public Short getHighestValue() {
            return 100;
        }

        @Override
        public Short getLowerValue() {
            return 25;
        }

        @Override
        public Short getLowestValue() {
            return 0;
        }
    }

    public static class ShortVerifierBaseNumberVerifierTest extends BaseNumberVerifierTestCase<Short, ShortVerifier> {

        @Override
        protected ShortVerifier createCustomVerifier() {
            return new ShortVerifier(getMockVerification());
        }

        @Override
        public Short getEvenValue() {
            return 2;
        }

        @Override
        public Short getOddValue() {
            return 1;
        }

        @Override
        public Short getPositiveOneValue() {
            return 1;
        }

        @Override
        public Short getNegativeOneValue() {
            return -1;
        }

        @Override
        public Short getZeroValue() {
            return 0;
        }
    }

    public static class ShortVerifierBaseTruthVerifierTest extends BaseTruthVerifierTestCase<Short, ShortVerifier> {

        @Override
        protected ShortVerifier createCustomVerifier() {
            return new ShortVerifier(getMockVerification());
        }

        @Override
        protected Short[] getFalsyValues() {
            return new Short[]{0};
        }

        @Override
        protected Short[] getTruthyValues() {
            return new Short[]{1};
        }
    }
}
