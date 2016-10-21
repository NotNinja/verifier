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
 * Tests for the {@link ByteVerifier} class.
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class ByteVerifierTest {

    public static class ByteVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Byte, ByteVerifier> {

        @Override
        protected ByteVerifier createCustomVerifier() {
            return new ByteVerifier(getMockVerification());
        }

        @Override
        protected Byte createValueOne() {
            return new Byte((byte) 123);
        }

        @Override
        protected Byte createValueTwo() {
            return new Byte((byte) 321);
        }

        @Override
        protected Class<?> getParentClass() {
            return Number.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return Byte.class;
        }
    }

    public static class ByteVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<Byte, ByteVerifier> {

        @Override
        protected ByteVerifier createCustomVerifier() {
            return new ByteVerifier(getMockVerification());
        }

        @Override
        public Byte getBaseValue() {
            return 50;
        }

        @Override
        public Byte getHigherValue() {
            return 75;
        }

        @Override
        public Byte getHighestValue() {
            return 100;
        }

        @Override
        public Byte getLowerValue() {
            return 25;
        }

        @Override
        public Byte getLowestValue() {
            return 0;
        }
    }

    public static class ByteVerifierBaseNumberVerifierTest extends BaseNumberVerifierTestCase<Byte, ByteVerifier> {

        @Override
        protected ByteVerifier createCustomVerifier() {
            return new ByteVerifier(getMockVerification());
        }

        @Override
        public Byte getEvenValue() {
            return 2;
        }

        @Override
        public Byte getOddValue() {
            return 1;
        }

        @Override
        public Byte getPositiveOneValue() {
            return 1;
        }

        @Override
        public Byte getNegativeOneValue() {
            return -1;
        }

        @Override
        public Byte getZeroValue() {
            return 0;
        }
    }

    public static class ByteVerifierBaseTruthVerifierTest extends BaseTruthVerifierTestCase<Byte, ByteVerifier> {

        @Override
        protected ByteVerifier createCustomVerifier() {
            return new ByteVerifier(getMockVerification());
        }

        @Override
        protected Byte[] getFalsehoodValues() {
            return new Byte[]{0};
        }

        @Override
        protected Byte[] getTruthValues() {
            return new Byte[]{1};
        }
    }
}
