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

import org.junit.Ignore;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.CustomVerifierTestCaseBase;
import io.skelp.verifier.type.base.BaseComparableVerifierTestCase;
import io.skelp.verifier.type.base.BaseTruthVerifierTestCase;

/**
 * Tests for the {@link StringVerifier} class.
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class StringVerifierTest {

    public static class StringVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<String, StringVerifier> {

        @Override
        protected StringVerifier createCustomVerifier() {
            return new StringVerifier(getMockVerification());
        }

        @Override
        protected String createValueOne() {
            return new String("foo");
        }

        @Override
        protected String createValueTwo() {
            return new String("bar");
        }

        @Override
        protected Class<?> getParentClass() {
            return Object.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return String.class;
        }
    }

    public static class StringVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<String, StringVerifier> {

        @Override
        protected StringVerifier createCustomVerifier() {
            return new StringVerifier(getMockVerification());
        }

        @Override
        protected String getBaseValue() {
            return "m";
        }

        @Override
        protected String getHigherValue() {
            return "t";
        }

        @Override
        protected String getHighestValue() {
            return "z";
        }

        @Override
        protected String getLowerValue() {
            return "f";
        }

        @Override
        protected String getLowestValue() {
            return "a";
        }
    }

    public static class StringVerifierBaseTruthVerifierTest extends BaseTruthVerifierTestCase<String, StringVerifier> {

        @Override
        protected StringVerifier createCustomVerifier() {
            return new StringVerifier(getMockVerification());
        }

        @Override
        protected String[] getFalsehoodValues() {
            return new String[]{"false", "FALSE"};
        }

        @Override
        protected String[] getTruthValues() {
            return new String[]{"true", "TRUE"};
        }
    }

    @Ignore
    public static class StringVerifierMiscTest extends CustomVerifierTestCaseBase<String, StringVerifier> {

        // TODO: Complete

        @Override
        protected StringVerifier createCustomVerifier() {
            return new StringVerifier(getMockVerification());
        }
    }
}
