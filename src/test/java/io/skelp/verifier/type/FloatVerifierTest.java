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
 * Tests for the {@link FloatVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class FloatVerifierTest {

    public static class FloatVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Float, FloatVerifier> {

        @Override
        protected FloatVerifier createCustomVerifier() {
            return new FloatVerifier(getMockVerification());
        }

        @Override
        protected Float createValueOne() {
            return new Float(123);
        }

        @Override
        protected Float createValueTwo() {
            return new Float(321);
        }

        @Override
        protected Class<?> getParentClass() {
            return Number.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return Float.class;
        }
    }

    public static class FloatVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<Float, FloatVerifier> {

        @Override
        protected FloatVerifier createCustomVerifier() {
            return new FloatVerifier(getMockVerification());
        }

        @Override
        public Float getBaseValue() {
            return 50F;
        }

        @Override
        public Float getHigherValue() {
            return 75F;
        }

        @Override
        public Float getHighestValue() {
            return 100F;
        }

        @Override
        public Float getLowerValue() {
            return 25F;
        }

        @Override
        public Float getLowestValue() {
            return 0F;
        }
    }

    public static class FloatVerifierBaseNumberVerifierTest extends BaseNumberVerifierTestCase<Float, FloatVerifier> {

        @Override
        protected FloatVerifier createCustomVerifier() {
            return new FloatVerifier(getMockVerification());
        }

        @Override
        public Float getEvenValue() {
            return 2F;
        }

        @Override
        public Float getOddValue() {
            return 1F;
        }

        @Override
        public Float getPositiveOneValue() {
            return 1F;
        }

        @Override
        public Float getNegativeOneValue() {
            return -1F;
        }

        @Override
        public Float getZeroValue() {
            return 0F;
        }
    }

    public static class FloatVerifierBaseTruthVerifierTest extends BaseTruthVerifierTestCase<Float, FloatVerifier> {

        @Override
        protected FloatVerifier createCustomVerifier() {
            return new FloatVerifier(getMockVerification());
        }

        @Override
        protected Float[] getFalsehoodValues() {
            return new Float[]{0F};
        }

        @Override
        protected Float[] getTruthValues() {
            return new Float[]{1F};
        }
    }
}
