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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.CustomVerifierTestCaseBase;
import io.skelp.verifier.type.base.BaseComparableVerifier;
import io.skelp.verifier.type.base.BaseTruthVerifierTestCase;

/**
 * <p>
 * Tests for the {@link BooleanVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class BooleanVerifierTest {

    public static class BooleanVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Boolean, BooleanVerifier> {

        @Override
        protected BooleanVerifier createCustomVerifier() {
            return new BooleanVerifier(getMockVerification());
        }

        @Override
        protected Boolean createValueOne() {
            return new Boolean(false);
        }

        @Override
        protected Boolean createValueTwo() {
            return new Boolean(true);
        }

        @Override
        protected Class<?> getParentClass() {
            return Object.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return Boolean.class;
        }
    }

    public static class BooleanVerifierBaseComparableVerifierTest extends CustomVerifierTestCaseBase<Boolean, BooleanVerifier> {

        @Test
        public void testBetweenWhenValueIsFalse() {
            testBetweenHelper(false);
        }

        @Test
        public void testBetweenWhenValueIsTrue() {
            testBetweenHelper(true);
        }

        private void testBetweenHelper(Boolean value) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().between(false, true));

            verify(getMockVerification()).report(eq(true), eq(BaseComparableVerifier.MessageKeys.BETWEEN), getArgsCaptor().capture());

            assertArrayEquals("Passes start and end for message formatting", new Object[]{false, true}, getArgsCaptor().getAllValues().toArray());
        }

        @Test
        public void testBetweenExclusiveWhenValueIsFalse() {
            testBetweenExclusiveHelper(false);
        }

        @Test
        public void testBetweenExclusiveWhenValueIsTrue() {
            testBetweenExclusiveHelper(true);
        }

        private void testBetweenExclusiveHelper(Boolean value) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().betweenExclusive(false, true));

            verify(getMockVerification()).report(eq(false), eq(BaseComparableVerifier.MessageKeys.BETWEEN_EXCLUSIVE), getArgsCaptor().capture());

            assertArrayEquals("Passes start and end for message formatting", new Object[]{false, true}, getArgsCaptor().getAllValues().toArray());
        }

        @Test
        public void testLessThanWhenValueIsEqualToOther() {
            testLessThanHelper(false, false, false);
        }

        @Test
        public void testLessThanWhenValueIsGreaterThanOther() {
            testLessThanHelper(true, false, false);
        }

        @Test
        public void testLessThanWhenValueIsLessThanOther() {
            testLessThanHelper(false, true, true);
        }

        private void testLessThanHelper(Boolean value, Boolean other, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThan(other));

            verify(getMockVerification()).report(eq(expected), eq(BaseComparableVerifier.MessageKeys.LESS_THAN), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testLessThanOrEqualToWhenValueIsEqualToOther() {
            testLessThanOrEqualToHelper(false, false, true);
        }

        @Test
        public void testLessThanOrEqualToWhenValueIsGreaterThanOther() {
            testLessThanOrEqualToHelper(true, false, false);
        }

        @Test
        public void testLessThanOrEqualToWhenValueIsLessThanOther() {
            testLessThanOrEqualToHelper(false, true, true);
        }

        private void testLessThanOrEqualToHelper(Boolean value, Boolean other, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lessThanOrEqualTo(other));

            verify(getMockVerification()).report(eq(expected), eq(BaseComparableVerifier.MessageKeys.LESS_THAN_OR_EQUAL_TO), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testGreaterThanWhenValueIsEqualToOther() {
            testGreaterThanHelper(false, false, false);
        }

        @Test
        public void testGreaterThanWhenValueIsGreaterThanOther() {
            testGreaterThanHelper(true, false, true);
        }

        @Test
        public void testGreaterWhenValueIsLessThanOther() {
            testGreaterThanHelper(false, true, false);
        }

        private void testGreaterThanHelper(Boolean value, Boolean other, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThan(other));

            verify(getMockVerification()).report(eq(expected), eq(BaseComparableVerifier.MessageKeys.GREATER_THAN), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testGreaterThanOrEqualToWhenValueIsEqualToOther() {
            testGreaterThanOrEqualToHelper(false, false, true);
        }

        @Test
        public void testGreaterThanOrEqualToWhenValueIsGreaterThanOther() {
            testGreaterThanOrEqualToHelper(true, false, true);
        }

        @Test
        public void testGreaterThanOrEqualToWhenValueIsLessThanOther() {
            testGreaterThanOrEqualToHelper(false, true, false);
        }

        private void testGreaterThanOrEqualToHelper(Boolean value, Boolean other, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().greaterThanOrEqualTo(other));

            verify(getMockVerification()).report(eq(expected), eq(BaseComparableVerifier.MessageKeys.GREATER_THAN_OR_EQUAL_TO), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Override
        protected BooleanVerifier createCustomVerifier() {
            return new BooleanVerifier(getMockVerification());
        }
    }

    public static class BooleanVerifierBaseTruthVerifierTest extends BaseTruthVerifierTestCase<Boolean, BooleanVerifier> {

        @Override
        protected BooleanVerifier createCustomVerifier() {
            return new BooleanVerifier(getMockVerification());
        }

        @Override
        protected Boolean[] getFalsyValues() {
            return new Boolean[]{false};
        }

        @Override
        protected Boolean[] getTruthyValues() {
            return new Boolean[]{true};
        }
    }
}
