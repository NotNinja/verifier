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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.notninja.verifier.AbstractCustomVerifierTestCase;
import org.notninja.verifier.CustomVerifierTestCaseBase;
import org.notninja.verifier.message.MessageKeyEnumTestCase;

/**
 * <p>
 * Tests for the {@link ThrowableVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class ThrowableVerifierTest {

    public static class ThrowableVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Throwable, ThrowableVerifier> {

        @Override
        protected ThrowableVerifier createCustomVerifier() {
            return new ThrowableVerifier(getMockVerification());
        }

        @Override
        protected Throwable createValueOne() {
            return new CheckedException("foo", null);
        }

        @Override
        protected Throwable createValueTwo() {
            return new CheckedException("bar", null);
        }

        @Override
        protected Class<?> getParentClass() {
            return Exception.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return CheckedException.class;
        }
    }

    public static class ThrowableVerifierMiscTest extends CustomVerifierTestCaseBase<Throwable, ThrowableVerifier> {

        @Test
        public void testCausedByWithClassWhenValueHasCircularCause() {
            testCausedByWithClassHelper(new CheckedException(null, new CircularException()), CircularException.class, true);
        }

        @Test
        public void testCausedByWithClassWhenValueHasCausesWithDifferentType() {
            testCausedByWithClassHelper(new CheckedException(null, new NullPointerException()), UncheckedException.class, false);
        }

        @Test
        public void testCausedByWithClassWhenValueHasCausesWithSameType() {
            testCausedByWithClassHelper(new CheckedException(null, new UncheckedException(null, null)), UncheckedException.class, true);
        }

        @Test
        public void testCausedByWithClassWhenValueHasDifferentTypeAndNoCause() {
            testCausedByWithClassHelper(new CheckedException(null, null), UncheckedException.class, false);
        }

        @Test
        public void testCausedByWithClassWhenValueHasSameTypeAndNoCause() {
            testCausedByWithClassHelper(new CheckedException(null, null), CheckedException.class, true);
        }

        @Test
        public void testCausedByWithClassWhenValueIsNull() {
            testCausedByWithClassHelper(null, Exception.class, false);
        }

        private void testCausedByWithClassHelper(Throwable value, Class<?> type, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().causedBy(type));

            verify(getMockVerification()).report(eq(expected), eq(ThrowableVerifier.MessageKeys.CAUSED_BY), getArgsCaptor().capture());

            assertSame("Passes type for message formatting", type, getArgsCaptor().getValue());
        }

        @Test
        public void testCausedByWithThrowableWhenValueHasCircularCause() {
            Throwable cause = new CircularException();

            testCausedByWithThrowableHelper(new CheckedException(null, cause), cause, true);
        }

        @Test
        public void testCausedByWithThrowableWhenValueHasDifferentCauses() {
            testCausedByWithThrowableHelper(new CheckedException(null, new NullPointerException()), new UncheckedException(null, null), false);
        }

        @Test
        public void testCausedByWithThrowableWhenValueHasSameCause() {
            Throwable cause = new UncheckedException(null, null);

            testCausedByWithThrowableHelper(new CheckedException(null, cause), cause, true);
        }

        @Test
        public void testCausedByWithThrowableWhenValueIsDifferentAndHasNoCause() {
            testCausedByWithThrowableHelper(new CheckedException(null, null), new UncheckedException(null, null), false);
        }

        @Test
        public void testCausedByWithThrowableWhenValueIsSameAndHasNoCause() {
            Throwable cause = new CheckedException(null, null);

            testCausedByWithThrowableHelper(cause, cause, true);
        }

        @Test
        public void testCausedByWithThrowableWhenValueIsNull() {
            testCausedByWithThrowableHelper(null, new Exception(), false);
        }

        private void testCausedByWithThrowableHelper(Throwable value, Throwable cause, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().causedBy(cause));

            verify(getMockVerification()).report(eq(expected), eq(ThrowableVerifier.MessageKeys.CAUSED_BY), getArgsCaptor().capture());

            assertSame("Passes cause for message formatting", cause, getArgsCaptor().getValue());
        }

        @Test
        public void testCausedByWithThrowableAndNameWhenValueHasCircularCause() {
            Throwable cause = new CircularException();

            testCausedByWithThrowableAndNameHelper(new CheckedException(null, cause), cause, "cause", true);
        }

        @Test
        public void testCausedByWithThrowableAndNameWhenValueHasDifferentCauses() {
            testCausedByWithThrowableAndNameHelper(new CheckedException(null, new NullPointerException()), new UncheckedException(null, null), "cause", false);
        }

        @Test
        public void testCausedByWithThrowableAndNameWhenValueHasSameCause() {
            Throwable cause = new UncheckedException(null, null);

            testCausedByWithThrowableAndNameHelper(new CheckedException(null, cause), cause, "cause", true);
        }

        @Test
        public void testCausedByWithThrowableAndNameWhenValueIsDifferentAndHasNoCause() {
            testCausedByWithThrowableAndNameHelper(new CheckedException(null, null), new UncheckedException(null, null), "cause", false);
        }

        @Test
        public void testCausedByWithThrowableAndNameWhenValueIsSameAndHasNoCause() {
            Throwable cause = new CheckedException(null, null);

            testCausedByWithThrowableAndNameHelper(cause, cause, "cause", true);
        }

        @Test
        public void testCausedByWithThrowableAndNameWhenValueIsNull() {
            testCausedByWithThrowableAndNameHelper(null, new Exception(), "cause", false);
        }

        private void testCausedByWithThrowableAndNameHelper(Throwable value, Throwable cause, Object name, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().causedBy(cause, name));

            verify(getMockVerification()).report(eq(expected), eq(ThrowableVerifier.MessageKeys.CAUSED_BY), getArgsCaptor().capture());

            assertSame("Passes name for message formatting", name, getArgsCaptor().getValue());
        }

        @Test
        public void testCausedByAllWhenNoTypes() {
            testCausedByAllHelper(new CheckedException(null, null), createEmptyArray(Class.class), true);
        }

        @Test
        public void testCausedByAllWhenTypeIsNull() {
            testCausedByAllHelper(new CheckedException(null, null), createArray((Class) null), false);
        }

        @Test
        public void testCausedByAllWhenTypesIsNull() {
            testCausedByAllHelper(new CheckedException(null, null), null, true);
        }

        @Test
        public void testCausedByAllWhenValueCausedByAllTypes() {
            testCausedByAllHelper(new CheckedException(null, new UncheckedException(null, null)), createArray(CheckedException.class, UncheckedException.class, Throwable.class), true);
        }

        @Test
        public void testCausedByAllWhenValueCausedBySomeTypes() {
            testCausedByAllHelper(new CheckedException(null, new UncheckedException(null, null)), createArray(IllegalArgumentException.class, IllegalStateException.class, UncheckedException.class), false);
        }

        @Test
        public void testCausedByAllWhenValueNotCausedByAnyType() {
            testCausedByAllHelper(new CheckedException(null, new UncheckedException(null, null)), createArray(IllegalArgumentException.class, IllegalStateException.class, NullPointerException.class), false);
        }

        @Test
        public void testCausedByAllWhenValueIsNull() {
            testCausedByAllHelper(null, createArray(Throwable.class), false);
        }

        private void testCausedByAllHelper(Throwable value, Class<?>[] types, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().causedByAll(types));

            verify(getMockVerification()).report(expected, ThrowableVerifier.MessageKeys.CAUSED_BY_ALL, (Object) types);
        }

        @Test
        public void testCausedByAnyWithClassesWhenNoTypes() {
            testCausedByAnyWithClassesHelper(new CheckedException(null, null), createEmptyArray(Class.class), false);
        }

        @Test
        public void testCausedByAnyWithClassesWhenTypeIsNull() {
            testCausedByAnyWithClassesHelper(new CheckedException(null, null), createArray((Class) null), false);
        }

        @Test
        public void testCausedByAnyWithClassesWhenTypesIsNull() {
            testCausedByAnyWithClassesHelper(new CheckedException(null, null), null, false);
        }

        @Test
        public void testCausedByAnyWithClassesWhenValueCausedByAllTypes() {
            testCausedByAnyWithClassesHelper(new CheckedException(null, new UncheckedException(null, null)), createArray(CheckedException.class, UncheckedException.class, Throwable.class), true);
        }

        @Test
        public void testCausedByAnyWithClassesWhenValueCausedBySomeTypes() {
            testCausedByAnyWithClassesHelper(new CheckedException(null, new UncheckedException(null, null)), createArray(IllegalArgumentException.class, IllegalStateException.class, UncheckedException.class), true);
        }

        @Test
        public void testCausedByAnyWithClassesWhenValueNotCausedByAnyType() {
            testCausedByAnyWithClassesHelper(new CheckedException(null, new UncheckedException(null, null)), createArray(IllegalArgumentException.class, IllegalStateException.class, NullPointerException.class), false);
        }

        @Test
        public void testCausedByAnyWithClassesWhenValueIsNull() {
            testCausedByAnyWithClassesHelper(null, createArray(Throwable.class), false);
        }

        private void testCausedByAnyWithClassesHelper(Throwable value, Class<?>[] types, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().causedByAny(types));

            verify(getMockVerification()).report(expected, ThrowableVerifier.MessageKeys.CAUSED_BY_ANY, (Object) types);
        }

        @Test
        public void testCausedByAnyWithThrowablesWhenNoCauses() {
            testCausedByAnyWithThrowablesHelper(new CheckedException(null, null), createEmptyArray(Throwable.class), false);
        }

        @Test
        public void testCausedByAnyWithThrowablesWhenCauseIsNull() {
            testCausedByAnyWithThrowablesHelper(new CheckedException(null, null), createArray((Throwable) null), false);
        }

        @Test
        public void testCausedByAnyWithThrowablesWhenCausesIsNull() {
            testCausedByAnyWithThrowablesHelper(new CheckedException(null, null), null, false);
        }

        @Test
        public void testCausedByAnyWithThrowablesWhenValueCausedByAllCauses() {
            Throwable cause = new UncheckedException(null, null);
            Throwable value = new CheckedException(null, cause);

            testCausedByAnyWithThrowablesHelper(value, createArray(value, cause), true);
        }

        @Test
        public void testCausedByAnyWithThrowablesWhenValueCausedBySomeCauses() {
            Throwable cause = new UncheckedException(null, null);

            testCausedByAnyWithThrowablesHelper(new CheckedException(null, cause), createArray(new IllegalArgumentException(), cause), true);
        }

        @Test
        public void testCausedByAnyWithThrowablesWhenValueNotCausedByAnyCause() {
            testCausedByAnyWithThrowablesHelper(new CheckedException(null, new UncheckedException(null, null)), createArray(new IllegalArgumentException(), new NullPointerException()), false);
        }

        @Test
        public void testCausedByAnyWithThrowablesWhenValueIsNull() {
            testCausedByAnyWithThrowablesHelper(null, createArray(new Throwable()), false);
        }

        private void testCausedByAnyWithThrowablesHelper(Throwable value, Throwable[] causes, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().causedByAny(causes));

            verify(getMockVerification()).report(expected, ThrowableVerifier.MessageKeys.CAUSED_BY_ANY, (Object) causes);
        }

        @Test
        public void testCheckedWhenValueIsChecked() {
            testCheckedHelper(new CheckedException(null, null), true);
        }

        @Test
        public void testCheckedWhenValueIsNull() {
            testCheckedHelper(null, false);
        }

        @Test
        public void testCheckedWhenValueIsUnchecked() {
            testCheckedHelper(new UncheckedException(null, null), false);
        }

        private void testCheckedHelper(Throwable value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().checked());

            verify(getMockVerification()).report(expected, ThrowableVerifier.MessageKeys.CHECKED);
        }

        @Test
        public void testMessageWhenValueHasDifferentMessage() {
            testMessageHelper(new CheckedException("foo", null), "bar", false);
        }

        @Test
        public void testMessageWhenValueIsNull() {
            testMessageHelper(null, "foo", false);
        }

        @Test
        public void testMessageWhenValueHasNullMessageAndMessageIsNotNull() {
            testMessageHelper(new CheckedException(null, null), "foo", false);
        }

        @Test
        public void testMessageWhenValueHasNullMessageAndMessageIsNull() {
            testMessageHelper(new CheckedException(null, null), null, true);
        }

        @Test
        public void testMessageWhenValueHasSameMessage() {
            testMessageHelper(new CheckedException("foo", null), "foo", true);
        }

        private void testMessageHelper(Throwable value, String message, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().message(message));

            verify(getMockVerification()).report(eq(expected), eq(ThrowableVerifier.MessageKeys.MESSAGE), getArgsCaptor().capture());

            assertSame("Passes message for message formatting", message, getArgsCaptor().getValue());
        }

        @Test
        public void testUncheckedWhenValueIsChecked() {
            testUncheckedHelper(new CheckedException(null, null), false);
        }

        @Test
        public void testUncheckedWhenValueIsNull() {
            testUncheckedHelper(null, false);
        }

        @Test
        public void testUncheckedWhenValueIsUnchecked() {
            testUncheckedHelper(new UncheckedException(null, null), true);
        }

        private void testUncheckedHelper(Throwable value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().unchecked());

            verify(getMockVerification()).report(expected, ThrowableVerifier.MessageKeys.UNCHECKED);
        }

        @Override
        protected ThrowableVerifier createCustomVerifier() {
            return new ThrowableVerifier(getMockVerification());
        }
    }

    public static class ThrowableVerifierMessageKeysTest extends MessageKeyEnumTestCase<ThrowableVerifier.MessageKeys> {

        @Override
        protected Class<? extends Enum> getEnumClass() {
            return ThrowableVerifier.MessageKeys.class;
        }

        @Override
        protected Map<String, String> getMessageKeys() {
            Map<String, String> messageKeys = new HashMap<>();
            messageKeys.put("CAUSED_BY", "org.notninja.verifier.type.ThrowableVerifier.causedBy");
            messageKeys.put("CAUSED_BY_ALL", "org.notninja.verifier.type.ThrowableVerifier.causedByAll");
            messageKeys.put("CAUSED_BY_ANY", "org.notninja.verifier.type.ThrowableVerifier.causedByAny");
            messageKeys.put("CHECKED", "org.notninja.verifier.type.ThrowableVerifier.checked");
            messageKeys.put("MESSAGE", "org.notninja.verifier.type.ThrowableVerifier.message");
            messageKeys.put("UNCHECKED", "org.notninja.verifier.type.ThrowableVerifier.unchecked");

            return messageKeys;
        }
    }

    private static class CheckedException extends Exception {

        CheckedException(String message, Throwable cause) {
            super(message, cause);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }

            CheckedException other = (CheckedException) obj;
            return getMessage() == null ? other.getMessage() == null : getMessage().equals(other.getMessage());
        }

        @Override
        public int hashCode() {
            return getMessage() == null ? 0 : getMessage().hashCode();
        }
    }

    private static class CircularException extends Exception {

        CircularException() {
            super();
        }

        @Override
        public synchronized Throwable getCause() {
            return this;
        }
    }

    private static class UncheckedException extends RuntimeException {

        UncheckedException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
