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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.regex.Pattern;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.CustomVerifierTestCaseBase;
import io.skelp.verifier.message.ArrayFormatter;
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

    public static class StringVerifierMiscTest extends CustomVerifierTestCaseBase<String, StringVerifier> {

        private static final String WHITESPACE = " \r\n\t";
        private static final String SPACE = " ";
        private static final String NUMERIC = "0123456789";
        private static final String EMPTY = "";
        private static final String ALPHA_LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
        private static final String ALPHA_UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String ALPHA = ALPHA_LOWER_CASE + ALPHA_UPPER_CASE;
        private static final String ALPHANUMERIC = ALPHA + NUMERIC;
        private static final String ASCII_PRINTABLE = SPACE + "!\"#$%&'()*+,-./" + NUMERIC + ":;<=>?@" + ALPHA_UPPER_CASE + "[\\]^_`" + ALPHA_LOWER_CASE + "{|}~";

        private static String nonAsciiPrintable;

        @BeforeClass
        public static void setUpClass() {
            StringBuilder extendedAscii = new StringBuilder();
            for (char ch = 128; ch < 256; ch++) {
                extendedAscii.append(ch);
            }

            nonAsciiPrintable = extendedAscii.toString();
        }

        @Test
        public void testAlphaWhenValueIsEmpty() {
            testAlphaHelper(EMPTY, true);
        }

        @Test
        public void testAlphaWhenValueIsNull() {
            testAlphaHelper(null, false);
        }

        @Test
        public void testAlphaWhenValueIsOnlyLettersAndNumbers() {
            testAlphaHelper(ALPHANUMERIC, false);
        }

        @Test
        public void testAlphaWhenValueIsOnlyLettersAndSpace() {
            testAlphaHelper(ALPHA + SPACE, false);
        }

        @Test
        public void testAlphaWhenValueIsOnlyLowerCaseLetters() {
            testAlphaHelper(ALPHA_LOWER_CASE, true);
        }

        @Test
        public void testAlphaWhenValueIsOnlyNumbers() {
            testAlphaHelper(NUMERIC, false);
        }

        @Test
        public void testAlphaWhenValueIsOnlyUpperCaseLetters() {
            testAlphaHelper(ALPHA_UPPER_CASE, true);
        }

        @Test
        public void testAlphaWhenValueIsOnlyWhitespace() {
            testAlphaHelper(WHITESPACE, false);
        }

        private void testAlphaHelper(String value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().alpha());

            verify(getMockVerification()).check(expected, "contain only letters");
        }

        @Test
        public void testAlphaSpaceWhenValueIsEmpty() {
            testAlphaSpaceHelper(EMPTY, true);
        }

        @Test
        public void testAlphaSpaceWhenValueIsNull() {
            testAlphaSpaceHelper(null, false);
        }

        @Test
        public void testAlphaSpaceWhenValueIsOnlyLettersAndNumbers() {
            testAlphaSpaceHelper(ALPHANUMERIC, false);
        }

        @Test
        public void testAlphaSpaceWhenValueIsOnlyLettersAndSpace() {
            testAlphaSpaceHelper(ALPHA + SPACE, true);
        }

        @Test
        public void testAlphaSpaceWhenValueIsOnlyLowerCaseLetters() {
            testAlphaSpaceHelper(ALPHA_LOWER_CASE, true);
        }

        @Test
        public void testAlphaSpaceWhenValueIsOnlyNumbers() {
            testAlphaSpaceHelper(NUMERIC, false);
        }

        @Test
        public void testAlphaSpaceWhenValueIsOnlyUpperCaseLetters() {
            testAlphaSpaceHelper(ALPHA_UPPER_CASE, true);
        }

        @Test
        public void testAlphaSpaceWhenValueIsOnlyWhitespace() {
            testAlphaSpaceHelper(WHITESPACE, false);
        }

        private void testAlphaSpaceHelper(String value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().alphaSpace());

            verify(getMockVerification()).check(expected, "contain only letters or space");
        }

        @Test
        public void testAlphanumericWhenValueIsEmpty() {
            testAlphanumericHelper(EMPTY, true);
        }

        @Test
        public void testAlphanumericWhenValueIsNull() {
            testAlphanumericHelper(null, false);
        }

        @Test
        public void testAlphanumericWhenValueIsOnlyLettersAndNumbers() {
            testAlphanumericHelper(ALPHANUMERIC, true);
        }

        @Test
        public void testAlphanumericWhenValueIsOnlyLettersAndNumbersAndSpace() {
            testAlphanumericHelper(ALPHANUMERIC + SPACE, false);
        }

        @Test
        public void testAlphanumericWhenValueIsOnlyLettersAndSpace() {
            testAlphanumericHelper(ALPHA + SPACE, false);
        }

        @Test
        public void testAlphanumericWhenValueIsOnlyLowerCaseLetters() {
            testAlphanumericHelper(ALPHA_LOWER_CASE, true);
        }

        @Test
        public void testAlphanumericWhenValueIsOnlyNumbers() {
            testAlphanumericHelper(NUMERIC, true);
        }

        @Test
        public void testAlphanumericWhenValueIsOnlyNumbersAndSpace() {
            testAlphanumericHelper(NUMERIC + SPACE, false);
        }

        @Test
        public void testAlphanumericWhenValueIsOnlyUpperCaseLetters() {
            testAlphanumericHelper(ALPHA_UPPER_CASE, true);
        }

        @Test
        public void testAlphanumericWhenValueIsOnlyWhitespace() {
            testAlphanumericHelper(WHITESPACE, false);
        }

        private void testAlphanumericHelper(String value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().alphanumeric());

            verify(getMockVerification()).check(expected, "contain only letters or digits");
        }

        @Test
        public void testAlphanumericSpaceWhenValueIsEmpty() {
            testAlphanumericSpaceHelper(EMPTY, true);
        }

        @Test
        public void testAlphanumericSpaceWhenValueIsNull() {
            testAlphanumericSpaceHelper(null, false);
        }

        @Test
        public void testAlphanumericSpaceWhenValueIsOnlyLettersAndNumbers() {
            testAlphanumericSpaceHelper(ALPHANUMERIC, true);
        }

        @Test
        public void testAlphanumericSpaceWhenValueIsOnlyLettersAndNumbersAndSpace() {
            testAlphanumericSpaceHelper(ALPHANUMERIC + SPACE, true);
        }

        @Test
        public void testAlphanumericSpaceWhenValueIsOnlyLettersAndSpace() {
            testAlphanumericSpaceHelper(ALPHA + SPACE, true);
        }

        @Test
        public void testAlphanumericSpaceWhenValueIsOnlyLowerCaseLetters() {
            testAlphanumericSpaceHelper(ALPHA_LOWER_CASE, true);
        }

        @Test
        public void testAlphanumericSpaceWhenValueIsOnlyNumbers() {
            testAlphanumericSpaceHelper(NUMERIC, true);
        }

        @Test
        public void testAlphanumericSpaceWhenValueIsOnlyNumbersAndSpace() {
            testAlphanumericSpaceHelper(NUMERIC + SPACE, true);
        }

        @Test
        public void testAlphanumericSpaceWhenValueIsOnlyUpperCaseLetters() {
            testAlphanumericSpaceHelper(ALPHA_UPPER_CASE, true);
        }

        @Test
        public void testAlphanumericSpaceWhenValueIsOnlyWhitespace() {
            testAlphanumericSpaceHelper(WHITESPACE, false);
        }

        private void testAlphanumericSpaceHelper(String value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().alphanumericSpace());

            verify(getMockVerification()).check(expected, "contain only letters or digits or space");
        }

        @Test
        public void testAsciiPrintableWhenValueIsEmpty() {
            testAsciiPrintableHelper(EMPTY, true);
        }

        @Test
        public void testAsciiPrintableWhenValueIsNull() {
            testAsciiPrintableHelper(null, false);
        }

        @Test
        public void testAsciiPrintableWhenValueIsOnlyAsciiPrintableCharacters() {
            testAsciiPrintableHelper(ASCII_PRINTABLE, true);
        }

        @Test
        public void testAsciiPrintableWhenValueIsMixtureOfAsciiPrintableCharacters() {
            testAsciiPrintableHelper(ASCII_PRINTABLE + nonAsciiPrintable, false);
        }

        @Test
        public void testAsciiPrintableWhenValueIsOnlyNonAsciiPrintableCharacters() {
            testAsciiPrintableHelper(nonAsciiPrintable, false);
        }

        private void testAsciiPrintableHelper(String value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().asciiPrintable());

            verify(getMockVerification()).check(expected, "contain only ASCII printable characters");
        }

        @Test
        public void testBlankWhenValueIsBlank() {
            testBlankHelper(WHITESPACE, true);
        }

        @Test
        public void testBlankWhenValueIsEmpty() {
            testBlankHelper(EMPTY, true);
        }

        @Test
        public void testBlankWhenValueIsNotBlank() {
            testBlankHelper(WHITESPACE + ALPHANUMERIC + WHITESPACE, false);
        }

        @Test
        public void testBlankWhenValueIsNull() {
            testBlankHelper(null, true);
        }

        private void testBlankHelper(String value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().blank());

            verify(getMockVerification()).check(expected, "be blank");
        }

        @Test
        public void testContainWhenOtherIsEmpty() {
            testContainHelper("foo bar fizz buzz", EMPTY, true);
        }

        @Test
        public void testContainWhenOtherIsNull() {
            testContainHelper(EMPTY, null, false);
        }

        @Test
        public void testContainWhenValueAndOtherAreEmpty() {
            testContainHelper(EMPTY, EMPTY, true);
        }

        @Test
        public void testContainWhenValueContainsOther() {
            testContainHelper("foo bar fizz buzz", "bar", true);
        }

        @Test
        public void testContainWhenValueContainsOtherCharSequence() {
            testContainHelper("foo bar fizz buzz", new StringWrapper("bar"), true);
        }

        @Test
        public void testContainWhenValueContainsOtherCharSequenceWithDifferentCase() {
            testContainHelper("foo bar fizz buzz", new StringWrapper("BAR"), false);
        }

        @Test
        public void testContainWhenValueContainsOtherWithDifferentCase() {
            testContainHelper("foo bar fizz buzz", "BAR", false);
        }

        @Test
        public void testContainWhenValueDoesNotContainOther() {
            testContainHelper("foo bar fizz buzz", "fu", false);
        }

        @Test
        public void testContainWhenValueDoesNotContainOtherCharSequence() {
            testContainHelper("foo bar fizz buzz", new StringWrapper("fu"), false);
        }

        @Test
        public void testContainWhenValueIsEmpty() {
            testContainHelper(EMPTY, "foo", false);
        }

        @Test
        public void testContainWhenValueIsExactMatch() {
            testContainHelper("foo bar fizz buzz", "foo bar fizz buzz", true);
        }

        @Test
        public void testContainWhenValueIsNull() {
            testContainHelper(null, EMPTY, false);
        }

        private void testContainHelper(String value, CharSequence other, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().contain(other));

            verify(getMockVerification()).check(eq(expected), eq("contain '%s'"), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testContainAnyWhenOtherIsEmpty() {
            testContainAnyHelper("foo bar fizz buzz", new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testContainAnyWhenOtherIsNull() {
            testContainAnyHelper(EMPTY, new CharSequence[]{null}, false);
        }

        @Test
        public void testContainAnyWhenOthersIsNull() {
            testContainAnyHelper(EMPTY, null, false);
        }

        @Test
        public void testContainAnyWhenValueAndOtherAreEmpty() {
            testContainAnyHelper(EMPTY, new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testContainAnyWhenValueContainsOther() {
            testContainAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "bar"}, true);
        }

        @Test
        public void testContainAnyWhenValueContainsOtherCharSequence() {
            testContainAnyHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("bar")}, true);
        }

        @Test
        public void testContainAnyWhenValueContainsOtherCharSequenceWithDifferentCase() {
            testContainAnyHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("BAR")}, false);
        }

        @Test
        public void testContainAnyWhenValueContainsOtherWithDifferentCase() {
            testContainAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "BAR"}, false);
        }

        @Test
        public void testContainAnyWhenValueDoesNotContainOther() {
            testContainAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz"}, false);
        }

        @Test
        public void testContainAnyWhenValueDoesNotContainOtherCharSequence() {
            testContainAnyHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz")}, false);
        }

        @Test
        public void testContainAnyWhenValueIsExactMatch() {
            testContainAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "foo bar fizz buzz"}, true);
        }

        @Test
        public void testContainAnyWhenValueIsEmpty() {
            testContainAnyHelper(EMPTY, new CharSequence[]{"foo"}, false);
        }

        @Test
        public void testContainAnyWhenValueIsNull() {
            testContainAnyHelper(null, new CharSequence[]{EMPTY}, false);
        }

        private void testContainAnyHelper(String value, CharSequence[] others, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().containAny(others));

            verify(getMockVerification()).check(eq(expected), eq("contain any %s"), getArgsCaptor().capture());

            Object capturedArg = getArgsCaptor().getValue();
            assertTrue("Passes array formatter for message formatting", capturedArg instanceof ArrayFormatter);
            @SuppressWarnings("unchecked")
            ArrayFormatter<Class<?>> arrayFormatter = (ArrayFormatter<Class<?>>) capturedArg;
            assertArrayEquals("Array formatter contains passed classes", others, arrayFormatter.getArray());
        }

        @Test
        public void testContainAnyIgnoreCaseWhenOtherIsEmpty() {
            testContainAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenOtherIsNull() {
            testContainAnyIgnoreCaseHelper(EMPTY, new CharSequence[]{null}, false);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenOthersIsNull() {
            testContainAnyIgnoreCaseHelper(EMPTY, null, false);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenValueAndOtherAreEmpty() {
            testContainAnyIgnoreCaseHelper(EMPTY, new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenValueContainsOther() {
            testContainAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "bar"}, true);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenValueContainsOtherCharSequence() {
            testContainAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("bar")}, true);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenValueContainsOtherCharSequenceWithDifferentCase() {
            testContainAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("BAR")}, true);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenValueContainsOtherWithDifferentCase() {
            testContainAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "BAR"}, true);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenValueDoesNotContainOther() {
            testContainAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz"}, false);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenValueDoesNotContainOtherCharSequence() {
            testContainAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz")}, false);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenValueIsEmpty() {
            testContainAnyIgnoreCaseHelper(EMPTY, new CharSequence[]{"foo"}, false);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenValueIsExactMatch() {
            testContainAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "foo BAR FIZZ buzz"}, true);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenValueIsNull() {
            testContainAnyIgnoreCaseHelper(null, new CharSequence[]{EMPTY}, false);
        }

        private void testContainAnyIgnoreCaseHelper(String value, CharSequence[] others, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().containAnyIgnoreCase(others));

            verify(getMockVerification()).check(eq(expected), eq("contain any %s (ignore case)"), getArgsCaptor().capture());

            Object capturedArg = getArgsCaptor().getValue();
            assertTrue("Passes array formatter for message formatting", capturedArg instanceof ArrayFormatter);
            @SuppressWarnings("unchecked")
            ArrayFormatter<Class<?>> arrayFormatter = (ArrayFormatter<Class<?>>) capturedArg;
            assertArrayEquals("Array formatter contains passed classes", others, arrayFormatter.getArray());
        }

        @Test
        public void testContainIgnoreCaseWhenOtherIsEmpty() {
            testContainIgnoreCaseHelper("foo bar fizz buzz", EMPTY, true);
        }

        @Test
        public void testContainIgnoreCaseWhenOtherIsNull() {
            testContainIgnoreCaseHelper(EMPTY, null, false);
        }

        @Test
        public void testContainIgnoreCaseWhenOtherIsSpace() {
            testContainIgnoreCaseHelper("foo bar fizz buzz", SPACE, true);
        }

        @Test
        public void testContainIgnoreCaseWhenOtherIsSpaceAndValueDoesNotContainSpace() {
            testContainIgnoreCaseHelper("foo", SPACE, false);
        }

        @Test
        public void testContainIgnoreCaseWhenValueAndOtherAreEmpty() {
            testContainIgnoreCaseHelper(EMPTY, EMPTY, true);
        }

        @Test
        public void testContainIgnoreCaseWhenValueContainsOther() {
            testContainIgnoreCaseHelper("foo bar fizz buzz", "bar", true);
        }

        @Test
        public void testContainIgnoreCaseWhenValueContainsOtherCharSequence() {
            testContainIgnoreCaseHelper("foo bar fizz buzz", new StringWrapper("bar"), true);
        }

        @Test
        public void testContainIgnoreCaseWhenValueContainsOtherCharSequenceWithDifferentCase() {
            testContainIgnoreCaseHelper("foo bar fizz buzz", new StringWrapper("BAR"), true);
        }

        @Test
        public void testContainIgnoreCaseWhenValueContainsOtherCharSequenceWithSpecialCharacterInDifferenceCase() {
            testContainIgnoreCaseHelper("foo bar fizz buzz", new StringWrapper("FİZZ"), true);
        }

        @Test
        public void testContainIgnoreCaseWhenValueContainsOtherWithDifferentCase() {
            testContainIgnoreCaseHelper("foo bar fizz buzz", "BAR", true);
        }

        @Test
        public void testContainIgnoreCaseWhenValueContainsOtherWithSpecialCharacterInDifferenceCase() {
            testContainIgnoreCaseHelper("foo bar fizz buzz", "FİZZ", true);
        }

        @Test
        public void testContainIgnoreCaseWhenValueDoesNotContainOther() {
            testContainIgnoreCaseHelper("foo bar fizz buzz", "fu", false);
        }

        @Test
        public void testContainIgnoreCaseWhenValueDoesNotContainOtherCharSequence() {
            testContainIgnoreCaseHelper("foo bar fizz buzz", new StringWrapper("fu"), false);
        }

        @Test
        public void testContainIgnoreCaseWhenValueIsEmpty() {
            testContainIgnoreCaseHelper(EMPTY, "foo", false);
        }

        @Test
        public void testContainIgnoreCaseWhenValueIsExactMatch() {
            testContainIgnoreCaseHelper("foo bar fizz buzz", "foo BAR FIZZ buzz", true);
        }

        @Test
        public void testContainIgnoreCaseWhenValueIsNull() {
            testContainIgnoreCaseHelper(null, EMPTY, false);
        }

        private void testContainIgnoreCaseHelper(String value, CharSequence other, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().containIgnoreCase(other));

            verify(getMockVerification()).check(eq(expected), eq("contain '%s' (ignore case)"), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testEmptyWhenValueIsBlank() {
            testEmptyHelper(WHITESPACE, false);
        }

        @Test
        public void testEmptyWhenValueIsEmpty() {
            testEmptyHelper(EMPTY, true);
        }

        @Test
        public void testEmptyWhenValueIsNotEmpty() {
            testEmptyHelper(ALPHANUMERIC, false);
        }

        @Test
        public void testEmptyWhenValueIsNull() {
            testEmptyHelper(null, true);
        }

        private void testEmptyHelper(String value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().empty());

            verify(getMockVerification()).check(expected, "be empty");
        }

        @Test
        public void testEndWithWhenOtherIsEmpty() {
            testEndWithHelper("foo bar fizz buzz", EMPTY, true);
        }

        @Test
        public void testEndWithWhenOtherIsNull() {
            testEndWithHelper(EMPTY, null, false);
        }

        @Test
        public void testEndWithWhenValueAndOtherAreEmpty() {
            testEndWithHelper(EMPTY, EMPTY, true);
        }

        @Test
        public void testEndWithWhenValueDoesNotContainOther() {
            testEndWithHelper("foo bar fizz buzz", "fu", false);
        }

        @Test
        public void testEndWithWhenValueDoesNotEndWithOther() {
            testEndWithHelper("foo bar fizz buzz", "fizz", false);
        }

        @Test
        public void testEndWithWhenValueDoesNotEndWithOtherCharSequence() {
            testEndWithHelper("foo bar fizz buzz", new StringWrapper("fizz"), false);
        }

        @Test
        public void testEndWithWhenValueEndsWithOther() {
            testEndWithHelper("foo bar fizz buzz", "buzz", true);
        }

        @Test
        public void testEndWithWhenValueEndsWithOtherCharSequence() {
            testEndWithHelper("foo bar fizz buzz", new StringWrapper("buzz"), true);
        }

        @Test
        public void testEndWithWhenValueEndsWithOtherCharSequenceWithDifferentCase() {
            testEndWithHelper("foo bar fizz buzz", new StringWrapper("BUZZ"), false);
        }

        @Test
        public void testEndWithWhenValueEndsWithOtherWithDifferentCase() {
            testEndWithHelper("foo bar fizz buzz", "BUZZ", false);
        }

        @Test
        public void testEndWithWhenValueIsEmpty() {
            testEndWithHelper(EMPTY, "foo", false);
        }

        @Test
        public void testEndWithWhenValueIsExactMatch() {
            testEndWithHelper("foo bar fizz buzz", "foo bar fizz buzz", true);
        }

        @Test
        public void testEndWithWhenValueIsNull() {
            testEndWithHelper(null, EMPTY, false);
        }

        private void testEndWithHelper(String value, CharSequence other, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().endWith(other));

            verify(getMockVerification()).check(eq(expected), eq("end with '%s'"), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testEndWithAnyWhenOtherIsEmpty() {
            testEndWithAnyHelper("foo bar fizz buzz", new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testEndWithAnyWhenOtherIsNull() {
            testEndWithAnyHelper(EMPTY, new CharSequence[]{null}, false);
        }

        @Test
        public void testEndWithAnyWhenOthersIsNull() {
            testEndWithAnyHelper(EMPTY, null, false);
        }

        @Test
        public void testEndWithAnyWhenValueAndOtherAreEmpty() {
            testEndWithAnyHelper(EMPTY, new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testEndWithAnyWhenValueDoesNotContainOther() {
            testEndWithAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz"}, false);
        }

        @Test
        public void testEndWithAnyWhenValueDoesNotEndWithOther() {
            testEndWithAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "fizz"}, false);
        }

        @Test
        public void testEndWithAnyWhenValueDoesNotEndWithOtherCharSequence() {
            testEndWithAnyHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("fizz")}, false);
        }

        @Test
        public void testEndWithAnyWhenValueEndsWithOther() {
            testEndWithAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "buzz"}, true);
        }

        @Test
        public void testEndWithAnyWhenValueEndsWithOtherCharSequence() {
            testEndWithAnyHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("buzz")}, true);
        }

        @Test
        public void testEndWithAnyWhenValueEndsWithOtherCharSequenceWithDifferentCase() {
            testEndWithAnyHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("BUZZ")}, false);
        }

        @Test
        public void testEndWithAnyWhenValueEndsWithOtherWithDifferentCase() {
            testEndWithAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "BUZZ"}, false);
        }

        @Test
        public void testEndWithAnyWhenValueIsEmpty() {
            testEndWithAnyHelper(EMPTY, new CharSequence[]{"foo"}, false);
        }

        @Test
        public void testEndWithAnyWhenValueIsExactMatch() {
            testEndWithAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "foo bar fizz buzz"}, true);
        }

        @Test
        public void testEndWithAnyWhenValueIsNull() {
            testEndWithAnyHelper(null, new CharSequence[]{EMPTY}, false);
        }

        private void testEndWithAnyHelper(String value, CharSequence[] others, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().endWithAny(others));

            verify(getMockVerification()).check(eq(expected), eq("end with any %s"), getArgsCaptor().capture());

            Object capturedArg = getArgsCaptor().getValue();
            assertTrue("Passes array formatter for message formatting", capturedArg instanceof ArrayFormatter);
            @SuppressWarnings("unchecked")
            ArrayFormatter<Class<?>> arrayFormatter = (ArrayFormatter<Class<?>>) capturedArg;
            assertArrayEquals("Array formatter contains passed classes", others, arrayFormatter.getArray());
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenOtherIsEmpty() {
            testEndWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenOtherIsNull() {
            testEndWithAnyIgnoreCaseHelper(EMPTY, new CharSequence[]{null}, false);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenOthersIsNull() {
            testEndWithAnyIgnoreCaseHelper(EMPTY, null, false);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenValueAndOtherAreEmpty() {
            testEndWithAnyIgnoreCaseHelper(EMPTY, new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenValueDoesNotContainOther() {
            testEndWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz"}, false);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenValueDoesNotEndWithOther() {
            testEndWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "fizz"}, false);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenValueDoesNotEndWithOtherCharSequence() {
            testEndWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("fizz")}, false);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenValueEndsWithOther() {
            testEndWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "buzz"}, true);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenValueEndsWithOtherCharSequence() {
            testEndWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("buzz")}, true);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenValueEndsWithOtherCharSequenceWithDifferentCase() {
            testEndWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("BUZZ")}, true);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenValueEndsWithOtherWithDifferentCase() {
            testEndWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "BUZZ"}, true);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenValueIsEmpty() {
            testEndWithAnyIgnoreCaseHelper(EMPTY, new CharSequence[]{"foo"}, false);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenValueIsExactMatch() {
            testEndWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "foo BAR FIZZ buzz"}, true);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenValueIsNull() {
            testEndWithAnyIgnoreCaseHelper(null, new CharSequence[]{EMPTY}, false);
        }

        private void testEndWithAnyIgnoreCaseHelper(String value, CharSequence[] others, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().endWithAnyIgnoreCase(others));

            verify(getMockVerification()).check(eq(expected), eq("end with any %s (ignore case)"), getArgsCaptor().capture());

            Object capturedArg = getArgsCaptor().getValue();
            assertTrue("Passes array formatter for message formatting", capturedArg instanceof ArrayFormatter);
            @SuppressWarnings("unchecked")
            ArrayFormatter<Class<?>> arrayFormatter = (ArrayFormatter<Class<?>>) capturedArg;
            assertArrayEquals("Array formatter contains passed classes", others, arrayFormatter.getArray());
        }

        @Test
        public void testEndWithIgnoreCaseWhenOtherIsEmpty() {
            testEndWithIgnoreCaseHelper("foo bar fizz buzz", EMPTY, true);
        }

        @Test
        public void testEndWithIgnoreCaseWhenOtherIsNull() {
            testEndWithIgnoreCaseHelper(EMPTY, null, false);
        }

        @Test
        public void testEndWithIgnoreCaseWhenValueAndOtherAreEmpty() {
            testEndWithIgnoreCaseHelper(EMPTY, EMPTY, true);
        }

        @Test
        public void testEndWithIgnoreCaseWhenValueDoesNotContainOther() {
            testEndWithIgnoreCaseHelper("foo bar fizz buzz", "fu", false);
        }

        @Test
        public void testEndWithIgnoreCaseWhenValueDoesNotEndWithOther() {
            testEndWithIgnoreCaseHelper("foo bar fizz buzz", "fizz", false);
        }

        @Test
        public void testEndWithIgnoreCaseWhenValueDoesNotEndWithOtherCharSequence() {
            testEndWithIgnoreCaseHelper("foo bar fizz buzz", new StringWrapper("fizz"), false);
        }

        @Test
        public void testEndWithIgnoreCaseWhenValueEndsWithOther() {
            testEndWithIgnoreCaseHelper("foo bar fizz buzz", "buzz", true);
        }

        @Test
        public void testEndWithIgnoreCaseWhenValueEndsWithOtherCharSequence() {
            testEndWithIgnoreCaseHelper("foo bar fizz buzz", new StringWrapper("buzz"), true);
        }

        @Test
        public void testEndWithIgnoreCaseWhenValueEndsWithOtherCharSequenceWithDifferentCase() {
            testEndWithIgnoreCaseHelper("foo bar fizz buzz", new StringWrapper("BUZZ"), true);
        }

        @Test
        public void testEndWithIgnoreCaseWhenValueEndsWithOtherWithDifferentCase() {
            testEndWithIgnoreCaseHelper("foo bar fizz buzz", "BUZZ", true);
        }

        @Test
        public void testEndWithIgnoreCaseWhenValueIsEmpty() {
            testEndWithIgnoreCaseHelper(EMPTY, "foo", false);
        }

        @Test
        public void testEndWithIgnoreCaseWhenValueIsExactMatch() {
            testEndWithIgnoreCaseHelper("foo bar fizz buzz", "foo BAR FIZZ buzz", true);
        }

        @Test
        public void testEndWithIgnoreCaseWhenValueIsNull() {
            testEndWithIgnoreCaseHelper(null, EMPTY, false);
        }

        private void testEndWithIgnoreCaseHelper(String value, CharSequence other, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().endWithIgnoreCase(other));

            verify(getMockVerification()).check(eq(expected), eq("end with '%s' (ignore case)"), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenOtherCharSequenceContainsSpecialCharacterInDifferenceCase() {
            testEqualToAnyIgnoreCaseHelper("fizz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("FİZZ")}, true);
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenOtherContainsSpecialCharacterInDifferenceCase() {
            testEqualToAnyIgnoreCaseHelper("fizz", new CharSequence[]{"fu", "baz", "FİZZ"}, true);
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenOtherIsNull() {
            testEqualToAnyIgnoreCaseHelper("foo", new CharSequence[]{null}, false);
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenOthersIsNull() {
            testEqualToAnyIgnoreCaseHelper("foo", null, false);
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenValueAndOtherIsNull() {
            testEqualToAnyIgnoreCaseHelper(null, new CharSequence[]{null}, true);
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenValueAndOthersIsNull() {
            testEqualToAnyIgnoreCaseHelper(null, null, false);
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenValueIsDifferentFromOthers() {
            testEqualToAnyIgnoreCaseHelper("foo", new CharSequence[]{"bar", "fu", new StringWrapper("baz")}, false);
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenValueIsEqualToOther() {
            testEqualToAnyIgnoreCaseHelper("foo", new CharSequence[]{"bar", "fu", "foo"}, true);
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenValueIsEqualToOtherCharSequence() {
            testEqualToAnyIgnoreCaseHelper("foo", new CharSequence[]{new StringWrapper("bar"), new StringWrapper("fu"), new StringWrapper("foo")}, true);
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenValueIsEqualToOtherCharSequenceIgnoringCase() {
            testEqualToAnyIgnoreCaseHelper("foo", new CharSequence[]{new StringWrapper("bar"), new StringWrapper("fu"), new StringWrapper("FOO")}, true);
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenValueIsEqualToOtherIgnoringCase() {
            testEqualToAnyIgnoreCaseHelper("foo", new CharSequence[]{"bar", "fu", "FOO"}, true);
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenValueIsNull() {
            testEqualToAnyIgnoreCaseHelper(null, new CharSequence[]{"foo", "bar", "fu"}, false);
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenValueIsSameAsOther() {
            String value = "foo";

            testEqualToAnyIgnoreCaseHelper(value, new CharSequence[]{"bar", "fu", value}, true);
        }

        private void testEqualToAnyIgnoreCaseHelper(String value, CharSequence[] others, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().equalToAnyIgnoreCase(others));

            verify(getMockVerification()).check(eq(expected), eq("be equal to any %s (ignore case)"), getArgsCaptor().capture());

            Object capturedArg = getArgsCaptor().getValue();
            assertTrue("Passes array formatter for message formatting", capturedArg instanceof ArrayFormatter);
            @SuppressWarnings("unchecked")
            ArrayFormatter<Class<?>> arrayFormatter = (ArrayFormatter<Class<?>>) capturedArg;
            assertArrayEquals("Array formatter contains passed classes", others, arrayFormatter.getArray());
        }

        @Test
        public void testEqualToIgnoreCaseWhenOtherCharSequenceContainsSpecialCharacterInDifferenceCase() {
            testEqualToIgnoreCaseHelper("fizz", new StringWrapper("FİZZ"), true);
        }

        @Test
        public void testEqualToIgnoreCaseWhenOtherContainsSpecialCharacterInDifferenceCase() {
            testEqualToIgnoreCaseHelper("fizz", "FİZZ", true);
        }

        @Test
        public void testEqualToIgnoreCaseWhenOtherIsNull() {
            testEqualToIgnoreCaseHelper("foo", null, false);
        }

        @Test
        public void testEqualToIgnoreCaseWhenValueAndOtherAreNull() {
            testEqualToIgnoreCaseHelper(null, null, true);
        }

        @Test
        public void testEqualToIgnoreCaseWhenValueIsDifferentFromOther() {
            testEqualToIgnoreCaseHelper("foo", "bar", false);
        }

        @Test
        public void testEqualToIgnoreCaseWhenValueIsDifferentFromOtherCharSequence() {
            testEqualToIgnoreCaseHelper("foo", new StringWrapper("bar"), false);
        }

        @Test
        public void testEqualToIgnoreCaseWhenValueIsEqualToOther() {
            testEqualToIgnoreCaseHelper(new String("foo"), new String("foo"), true);
        }

        @Test
        public void testEqualToIgnoreCaseWhenValueIsEqualToOtherCharSequence() {
            testEqualToIgnoreCaseHelper("foo", new StringWrapper("foo"), true);
        }

        @Test
        public void testEqualToIgnoreCaseWhenValueIsEqualToOtherCharSequenceIgnoringCase() {
            testEqualToIgnoreCaseHelper("foo", new StringWrapper("FOO"), true);
        }

        @Test
        public void testEqualToIgnoreCaseWhenValueIsEqualToOtherIgnoringCase() {
            testEqualToIgnoreCaseHelper("foo", "FOO", true);
        }

        @Test
        public void testEqualToIgnoreCaseWhenValueIsNull() {
            testEqualToIgnoreCaseHelper(null, "foo", false);
        }

        @Test
        public void testEqualToIgnoreCaseWhenValueIsSameAsOther() {
            String value = "foo";

            testEqualToIgnoreCaseHelper(value, value, true);
        }

        private void testEqualToIgnoreCaseHelper(String value, CharSequence other, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().equalToIgnoreCase(other));

            verify(getMockVerification()).check(eq(expected), eq("be equal to '%s' (ignore case)"), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testLengthWhenValueIsEmptyAndLengthIsDifferent() {
            testLengthHelper(EMPTY, 1, false);
        }

        @Test
        public void testLengthWhenValueIsEmptyAndLengthIsSame() {
            testLengthHelper(EMPTY, 0, true);
        }

        @Test
        public void testLengthWhenValueIsNotEmptyAndLengthIsDifferent() {
            testLengthHelper("foo", 4, false);
        }

        @Test
        public void testLengthWhenValueIsNotEmptyAndLengthIsSame() {
            testLengthHelper("foo", 3, true);
        }

        @Test
        public void testLengthWhenValueIsNullAndLengthIsNotZero() {
            testLengthHelper(null, 1, false);
        }

        @Test
        public void testLengthWhenValueIsNullAndLengthIsZero() {
            testLengthHelper(null, 0, true);
        }

        private void testLengthHelper(String value, int length, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().length(length));

            verify(getMockVerification()).check(eq(expected), eq("have a length of '%d'"), getArgsCaptor().capture());

            assertSame("Passes length for message formatting", length, getArgsCaptor().getValue());
        }

        @Test
        public void testLowerCaseWhenValueIsEmpty() {
            testLowerCaseHelper(EMPTY, true);
        }

        @Test
        public void testLowerCaseWhenValueIsNull() {
            testLowerCaseHelper(null, false);
        }

        @Test
        public void testLowerCaseWhenValueIsOnlyLettersAndNumbers() {
            testLowerCaseHelper(ALPHANUMERIC, false);
        }

        @Test
        public void testLowerCaseWhenValueIsOnlyLowerCaseLetters() {
            testLowerCaseHelper(ALPHA_LOWER_CASE, true);
        }

        @Test
        public void testLowerCaseWhenValueIsOnlyLowerCaseLettersAndSpace() {
            testLowerCaseHelper(ALPHA_LOWER_CASE + SPACE, false);
        }

        @Test
        public void testLowerCaseWhenValueIsOnlyNumbers() {
            testLowerCaseHelper(NUMERIC, false);
        }

        @Test
        public void testLowerCaseWhenValueIsOnlyUpperCaseLetters() {
            testLowerCaseHelper(ALPHA_UPPER_CASE, false);
        }

        @Test
        public void testLowerCaseWhenValueIsOnlyWhitespace() {
            testLowerCaseHelper(WHITESPACE, false);
        }

        private void testLowerCaseHelper(String value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().lowerCase());

            verify(getMockVerification()).check(expected, "be all lower case");
        }

        @Test
        public void testMatchWhenRegexIsNull() {
            testMatchHelper("foo", null, false);
        }

        @Test
        public void testMatchWhenValueIsEmptyAndMatch() {
            testMatchHelper(EMPTY, ".*", true);
        }

        @Test
        public void testMatchWhenValueIsEmptyAndNotMatch() {
            testMatchHelper(EMPTY, "fo{2}", false);
        }

        @Test
        public void testMatchWhenValueIsEmptyAndNotMatchAndOtherIsCharSequence() {
            testMatchHelper(EMPTY, new StringWrapper("fo{2}"), false);
        }

        @Test
        public void testMatchWhenValueIsMatch() {
            testMatchHelper("foo", "fo{2}", true);
        }

        @Test
        public void testMatchWhenValueIsMatchAndOtherIsCharSequence() {
            testMatchHelper("foo", new StringWrapper("fo{2}"), true);
        }

        @Test
        public void testMatchWhenValueIsNotMatch() {
            testMatchHelper("food", "fo{2}", false);
        }

        @Test
        public void testMatchWhenValueIsNull() {
            testMatchHelper(null, ".*", false);
        }

        private void testMatchHelper(String value, CharSequence regex, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().match(regex));

            verify(getMockVerification()).check(eq(expected), eq("match '%s'"), getArgsCaptor().capture());

            assertSame("Passes regex for message formatting", regex, getArgsCaptor().getValue());
        }

        @Test
        public void testMatchWithPatternWhenPatternIsNull() {
            testMatchWithPatternHelper("foo", null, false);
        }

        @Test
        public void testMatchWithPatternWhenValueIsEmptyAndMatch() {
            testMatchWithPatternHelper(EMPTY, Pattern.compile(".*"), true);
        }

        @Test
        public void testMatchWithPatternWhenValueIsEmptyAndNotMatch() {
            testMatchWithPatternHelper(EMPTY, Pattern.compile("fo{2}"), false);
        }

        @Test
        public void testMatchWithPatternWhenValueIsMatch() {
            testMatchWithPatternHelper("foo", Pattern.compile("fo{2}"), true);
        }

        @Test
        public void testMatchWithPatternWhenValueIsNotMatch() {
            testMatchWithPatternHelper("food", Pattern.compile("fo{2}"), false);
        }

        @Test
        public void testMatchWithPatternWhenValueIsNull() {
            testMatchWithPatternHelper(null, Pattern.compile(".*"), false);
        }

        private void testMatchWithPatternHelper(String value, Pattern pattern, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().match(pattern));

            verify(getMockVerification()).check(eq(expected), eq("match '%s'"), getArgsCaptor().capture());

            assertSame("Passes pattern for message formatting", pattern, getArgsCaptor().getValue());
        }

        @Test
        public void testNumericWhenValueIsEmpty() {
            testNumericHelper(EMPTY, true);
        }

        @Test
        public void testNumericWhenValueIsNull() {
            testNumericHelper(null, false);
        }

        @Test
        public void testNumericWhenValueIsOnlyLettersAndNumbers() {
            testNumericHelper(ALPHANUMERIC, false);
        }

        @Test
        public void testNumericWhenValueIsOnlyLowerCaseLetters() {
            testNumericHelper(ALPHA_LOWER_CASE, false);
        }

        @Test
        public void testNumericWhenValueIsOnlyNumbers() {
            testNumericHelper(NUMERIC, true);
        }

        @Test
        public void testNumericWhenValueIsOnlyNumbersAndSpace() {
            testNumericHelper(NUMERIC + SPACE, false);
        }

        @Test
        public void testNumericWhenValueIsOnlyUpperCaseLetters() {
            testNumericHelper(ALPHA_UPPER_CASE, false);
        }

        @Test
        public void testNumericWhenValueIsOnlyWhitespace() {
            testNumericHelper(WHITESPACE, false);
        }

        private void testNumericHelper(String value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().numeric());

            verify(getMockVerification()).check(expected, "contain only digits");
        }

        @Test
        public void testNumericSpaceWhenValueIsEmpty() {
            testNumericSpaceHelper(EMPTY, true);
        }

        @Test
        public void testNumericSpaceWhenValueIsNull() {
            testNumericSpaceHelper(null, false);
        }

        @Test
        public void testNumericSpaceWhenValueIsOnlyLettersAndNumbers() {
            testNumericSpaceHelper(ALPHANUMERIC, false);
        }

        @Test
        public void testNumericSpaceWhenValueIsOnlyLowerCaseLetters() {
            testNumericSpaceHelper(ALPHA_LOWER_CASE, false);
        }

        @Test
        public void testNumericSpaceWhenValueIsOnlyNumbers() {
            testNumericSpaceHelper(NUMERIC, true);
        }

        @Test
        public void testNumericSpaceWhenValueIsOnlyNumbersAndSpace() {
            testNumericSpaceHelper(NUMERIC + SPACE, true);
        }

        @Test
        public void testNumericSpaceWhenValueIsOnlyUpperCaseLetters() {
            testNumericSpaceHelper(ALPHA_UPPER_CASE, false);
        }

        @Test
        public void testNumericSpaceWhenValueIsOnlyWhitespace() {
            testNumericSpaceHelper(WHITESPACE, false);
        }

        private void testNumericSpaceHelper(String value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().numericSpace());

            verify(getMockVerification()).check(expected, "contain only digits or space");
        }

        @Test
        public void testStartWithWhenOtherIsEmpty() {
            testStartWithHelper("foo bar fizz buzz", EMPTY, true);
        }

        @Test
        public void testStartWithWhenOtherIsNull() {
            testStartWithHelper(EMPTY, null, false);
        }

        @Test
        public void testStartWithWhenValueAndOtherAreEmpty() {
            testStartWithHelper(EMPTY, EMPTY, true);
        }

        @Test
        public void testStartWithWhenValueDoesNotContainOther() {
            testStartWithHelper("foo bar fizz buzz", "fu", false);
        }

        @Test
        public void testStartWithWhenValueDoesNotStartWithOther() {
            testStartWithHelper("foo bar fizz buzz", "bar", false);
        }

        @Test
        public void testStartWithWhenValueDoesNotStartWithOtherCharSequence() {
            testStartWithHelper("foo bar fizz buzz", new StringWrapper("bar"), false);
        }

        @Test
        public void testStartWithWhenValueIsEmpty() {
            testStartWithHelper(EMPTY, "foo", false);
        }

        @Test
        public void testStartWithWhenValueIsExactMatch() {
            testStartWithHelper("foo bar fizz buzz", "foo bar fizz buzz", true);
        }

        @Test
        public void testStartWithWhenValueIsNull() {
            testStartWithHelper(null, EMPTY, false);
        }

        @Test
        public void testStartWithWhenValueStartsWithOther() {
            testStartWithHelper("foo bar fizz buzz", "foo", true);
        }

        @Test
        public void testStartWithWhenValueStartsWithOtherCharSequence() {
            testStartWithHelper("foo bar fizz buzz", new StringWrapper("foo"), true);
        }

        @Test
        public void testStartWithWhenValueStartsWithOtherCharSequenceWithDifferentCase() {
            testStartWithHelper("foo bar fizz buzz", new StringWrapper("FOO"), false);
        }

        @Test
        public void testStartWithWhenValueStartsWithOtherWithDifferentCase() {
            testStartWithHelper("foo bar fizz buzz", "FOO", false);
        }

        private void testStartWithHelper(String value, CharSequence other, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().startWith(other));

            verify(getMockVerification()).check(eq(expected), eq("start with '%s'"), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testStartWithAnyWhenOtherIsEmpty() {
            testStartWithAnyHelper("foo bar fizz buzz", new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testStartWithAnyWhenOtherIsNull() {
            testStartWithAnyHelper(EMPTY, new CharSequence[]{null}, false);
        }

        @Test
        public void testStartWithAnyWhenOthersIsNull() {
            testStartWithAnyHelper(EMPTY, null, false);
        }

        @Test
        public void testStartWithAnyWhenValueAndOtherAreEmpty() {
            testStartWithAnyHelper(EMPTY, new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testStartWithAnyWhenValueDoesNotContainOther() {
            testStartWithAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz"}, false);
        }

        @Test
        public void testStartWithAnyWhenValueDoesNotStartWithOther() {
            testStartWithAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "bar"}, false);
        }

        @Test
        public void testStartWithAnyWhenValueDoesNotStartWithOtherCharSequence() {
            testStartWithAnyHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("bar")}, false);
        }

        @Test
        public void testStartWithAnyWhenValueIsEmpty() {
            testStartWithAnyHelper(EMPTY, new CharSequence[]{"foo"}, false);
        }

        @Test
        public void testStartWithAnyWhenValueIsExactMatch() {
            testStartWithAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "foo bar fizz buzz"}, true);
        }

        @Test
        public void testStartWithAnyWhenValueIsNull() {
            testStartWithAnyHelper(null, new CharSequence[]{EMPTY}, false);
        }

        @Test
        public void testStartWithAnyWhenValueStartsWithOther() {
            testStartWithAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "foo"}, true);
        }

        @Test
        public void testStartWithAnyWhenValueStartsWithOtherCharSequence() {
            testStartWithAnyHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("foo")}, true);
        }

        @Test
        public void testStartWithAnyWhenValueStartsWithOtherCharSequenceWithDifferentCase() {
            testStartWithAnyHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("FOO")}, false);
        }

        @Test
        public void testStartWithAnyWhenValueStartsWithOtherWithDifferentCase() {
            testStartWithAnyHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "FOO"}, false);
        }

        private void testStartWithAnyHelper(String value, CharSequence[] others, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().startWithAny(others));

            verify(getMockVerification()).check(eq(expected), eq("start with any %s"), getArgsCaptor().capture());

            Object capturedArg = getArgsCaptor().getValue();
            assertTrue("Passes array formatter for message formatting", capturedArg instanceof ArrayFormatter);
            @SuppressWarnings("unchecked")
            ArrayFormatter<Class<?>> arrayFormatter = (ArrayFormatter<Class<?>>) capturedArg;
            assertArrayEquals("Array formatter contains passed classes", others, arrayFormatter.getArray());
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenOtherIsEmpty() {
            testStartWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenOtherIsNull() {
            testStartWithAnyIgnoreCaseHelper(EMPTY, new CharSequence[]{null}, false);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenOthersIsNull() {
            testStartWithAnyIgnoreCaseHelper(EMPTY, null, false);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenValueAndOtherAreEmpty() {
            testStartWithAnyIgnoreCaseHelper(EMPTY, new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenValueDoesNotContainOther() {
            testStartWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz"}, false);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenValueDoesNotStartWithOther() {
            testStartWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "bar"}, false);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenValueDoesNotStartWithOtherCharSequence() {
            testStartWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("bar")}, false);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenValueIsEmpty() {
            testStartWithAnyIgnoreCaseHelper(EMPTY, new CharSequence[]{"foo"}, false);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenValueIsExactMatch() {
            testStartWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "foo BAR FIZZ buzz"}, true);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenValueIsNull() {
            testStartWithAnyIgnoreCaseHelper(null, new CharSequence[]{EMPTY}, false);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenValueStartsWithOther() {
            testStartWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "foo"}, true);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenValueStartsWithOtherCharSequence() {
            testStartWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("foo")}, true);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenValueStartsWithOtherCharSequenceWithDifferentCase() {
            testStartWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz"), new StringWrapper("FOO")}, true);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenValueStartsWithOtherWithDifferentCase() {
            testStartWithAnyIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz", "FOO"}, true);
        }

        private void testStartWithAnyIgnoreCaseHelper(String value, CharSequence[] others, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().startWithAnyIgnoreCase(others));

            verify(getMockVerification()).check(eq(expected), eq("start with any %s (ignore case)"), getArgsCaptor().capture());

            Object capturedArg = getArgsCaptor().getValue();
            assertTrue("Passes array formatter for message formatting", capturedArg instanceof ArrayFormatter);
            @SuppressWarnings("unchecked")
            ArrayFormatter<Class<?>> arrayFormatter = (ArrayFormatter<Class<?>>) capturedArg;
            assertArrayEquals("Array formatter contains passed classes", others, arrayFormatter.getArray());
        }

        @Test
        public void testStartWithIgnoreCaseWhenOtherIsEmpty() {
            testStartWithIgnoreCaseHelper("foo bar fizz buzz", EMPTY, true);
        }

        @Test
        public void testStartWithIgnoreCaseWhenOtherIsNull() {
            testStartWithIgnoreCaseHelper(EMPTY, null, false);
        }

        @Test
        public void testStartWithIgnoreCaseWhenValueAndOtherAreEmpty() {
            testStartWithIgnoreCaseHelper(EMPTY, EMPTY, true);
        }

        @Test
        public void testStartWithIgnoreCaseWhenValueDoesNotContainOther() {
            testStartWithIgnoreCaseHelper("foo bar fizz buzz", "fu", false);
        }

        @Test
        public void testStartWithIgnoreCaseWhenValueDoesNotStartWithOther() {
            testStartWithIgnoreCaseHelper("foo bar fizz buzz", "bar", false);
        }

        @Test
        public void testStartWithIgnoreCaseWhenValueDoesNotStartWithOtherCharSequence() {
            testStartWithIgnoreCaseHelper("foo bar fizz buzz", new StringWrapper("bar"), false);
        }

        @Test
        public void testStartWithIgnoreCaseWhenValueIsEmpty() {
            testStartWithIgnoreCaseHelper(EMPTY, "foo", false);
        }

        @Test
        public void testStartWithIgnoreCaseWhenValueIsExactMatch() {
            testStartWithIgnoreCaseHelper("foo bar fizz buzz", "foo BAR FIZZ buzz", true);
        }

        @Test
        public void testStartWithIgnoreCaseWhenValueIsNull() {
            testStartWithIgnoreCaseHelper(null, EMPTY, false);
        }

        @Test
        public void testStartWithIgnoreCaseWhenValueStartsWithOther() {
            testStartWithIgnoreCaseHelper("foo bar fizz buzz", "foo", true);
        }

        @Test
        public void testStartWithIgnoreCaseWhenValueStartsWithOtherCharSequence() {
            testStartWithIgnoreCaseHelper("foo bar fizz buzz", new StringWrapper("foo"), true);
        }

        @Test
        public void testStartWithIgnoreCaseWhenValueStartsWithOtherCharSequenceWithDifferentCase() {
            testStartWithIgnoreCaseHelper("foo bar fizz buzz", new StringWrapper("fOO"), true);
        }

        @Test
        public void testStartWithIgnoreCaseWhenValueStartsWithOtherWithDifferentCase() {
            testStartWithIgnoreCaseHelper("foo bar fizz buzz", "FOO", true);
        }

        private void testStartWithIgnoreCaseHelper(String value, CharSequence other, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().startWithIgnoreCase(other));

            verify(getMockVerification()).check(eq(expected), eq("start with '%s' (ignore case)"), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testUpperCaseWhenValueIsEmpty() {
            testUpperCaseHelper(EMPTY, true);
        }

        @Test
        public void testUpperCaseWhenValueIsNull() {
            testUpperCaseHelper(null, false);
        }

        @Test
        public void testUpperCaseWhenValueIsOnlyLettersAndNumbers() {
            testUpperCaseHelper(ALPHANUMERIC, false);
        }

        @Test
        public void testUpperCaseWhenValueIsOnlyLowerCaseLetters() {
            testUpperCaseHelper(ALPHA_LOWER_CASE, false);
        }

        @Test
        public void testUpperCaseWhenValueIsOnlyNumbers() {
            testUpperCaseHelper(NUMERIC, false);
        }

        @Test
        public void testUpperCaseWhenValueIsOnlyUpperCaseLetters() {
            testUpperCaseHelper(ALPHA_UPPER_CASE, true);
        }

        @Test
        public void testUpperCaseWhenValueIsOnlyUpperCaseLettersAndSpace() {
            testUpperCaseHelper(ALPHA_UPPER_CASE + SPACE, false);
        }

        @Test
        public void testUpperCaseWhenValueIsOnlyWhitespace() {
            testUpperCaseHelper(WHITESPACE, false);
        }

        private void testUpperCaseHelper(String value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().upperCase());

            verify(getMockVerification()).check(expected, "be all upper case");
        }

        @Test
        public void testWhitespaceWhenValueIsEmpty() {
            testWhitespaceHelper(EMPTY, true);
        }

        @Test
        public void testWhitespaceWhenValueIsNull() {
            testWhitespaceHelper(null, false);
        }

        @Test
        public void testWhitespaceWhenValueIsOnlyLettersAndNumbers() {
            testWhitespaceHelper(ALPHANUMERIC, false);
        }

        @Test
        public void testWhitespaceWhenValueIsOnlyLettersAndNumbersAndWhitespace() {
            testWhitespaceHelper(ALPHANUMERIC + WHITESPACE, false);
        }

        @Test
        public void testWhitespaceWhenValueIsOnlyLowerCaseLetters() {
            testWhitespaceHelper(ALPHA_LOWER_CASE, false);
        }

        @Test
        public void testWhitespaceWhenValueIsOnlyLowerCaseLettersAndWhitespace() {
            testWhitespaceHelper(ALPHA_LOWER_CASE + WHITESPACE, false);
        }

        @Test
        public void testWhitespaceWhenValueIsOnlyNumbers() {
            testWhitespaceHelper(NUMERIC, false);
        }

        @Test
        public void testWhitespaceWhenValueIsOnlyNumbersAndWhitespace() {
            testWhitespaceHelper(NUMERIC + WHITESPACE, false);
        }

        @Test
        public void testWhitespaceWhenValueIsOnlyUpperCaseLetters() {
            testWhitespaceHelper(ALPHA_UPPER_CASE, false);
        }

        @Test
        public void testWhitespaceWhenValueIsOnlyUpperCaseLettersAndWhitespace() {
            testWhitespaceHelper(ALPHA_UPPER_CASE + WHITESPACE, false);
        }

        @Test
        public void testWhitespaceWhenValueIsOnlyWhitespace() {
            testWhitespaceHelper(WHITESPACE, true);
        }

        private void testWhitespaceHelper(String value, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().whitespace());

            verify(getMockVerification()).check(expected, "contain only whitespace");
        }

        @Override
        protected StringVerifier createCustomVerifier() {
            return new StringVerifier(getMockVerification());
        }
    }

    private static class StringWrapper implements CharSequence {

        final String str;

        StringWrapper(String str) {
            this.str = str;
        }

        @Override
        public char charAt(int index) {
            return str.charAt(index);
        }

        @Override
        public int length() {
            return str.length();
        }

        @Override
        public CharSequence subSequence(int beginIndex, int endIndex) {
            return str.subSequence(beginIndex, endIndex);
        }

        @Override
        public String toString() {
            return str;
        }
    }
}
