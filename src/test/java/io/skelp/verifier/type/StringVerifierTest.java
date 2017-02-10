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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.CustomVerifierTestCaseBase;
import io.skelp.verifier.message.MessageKeyEnumTestCase;
import io.skelp.verifier.type.base.BaseComparableVerifierTestCase;
import io.skelp.verifier.type.base.BaseTruthVerifierTestCase;

/**
 * <p>
 * Tests for the {@link StringVerifier} class.
 * </p>
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
        protected String[] getFalsyValues() {
            return new String[]{"", "false", "FALSE"};
        }

        @Override
        protected String[] getTruthyValues() {
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.ALPHA);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.ALPHA_SPACE);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.ALPHANUMERIC);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.ALPHANUMERIC_SPACE);
        }

        @Test
        public void testAsciiPrintableWhenValueIsAsciiControl() {
            testAsciiPrintableHelper("\0", false);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.ASCII_PRINTABLE);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.BLANK);
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

            verify(getMockVerification()).report(eq(expected), eq(StringVerifier.MessageKeys.CONTAIN), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testContainAllWhenNoOthers() {
            testContainAllHelper(EMPTY, new CharSequence[0], true);
        }

        @Test
        public void testContainAllWhenOtherIsEmpty() {
            testContainAllHelper("foo bar fizz buzz", new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testContainAllWhenOtherIsNull() {
            testContainAllHelper(EMPTY, new CharSequence[]{EMPTY, null}, false);
        }

        @Test
        public void testContainAllWhenOthersIsNull() {
            testContainAllHelper(EMPTY, null, true);
        }

        @Test
        public void testContainAllWhenValueAndOtherAreEmpty() {
            testContainAllHelper(EMPTY, new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testContainAllWhenValueContainsAllOthers() {
            testContainAllHelper("foo bar fizz buzz", new CharSequence[]{"foo", "bar", "fizz"}, true);
        }

        @Test
        public void testContainAllWhenValueContainsAllOthersCharSequence() {
            testContainAllHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("foo"), new StringWrapper("bar"), new StringWrapper("fizz")}, true);
        }

        @Test
        public void testContainAllWhenValueContainsAllOthersCharSequenceWithDifferentCase() {
            testContainAllHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("FOO"), new StringWrapper("BAR"), new StringWrapper("FIZZ")}, false);
        }

        @Test
        public void testContainAllWhenValueContainsAllOthersWithDifferentCase() {
            testContainAllHelper("foo bar fizz buzz", new CharSequence[]{"FOO", "BAR", "FIZZ"}, false);
        }

        @Test
        public void testContainAllWhenValueContainsSomeOthers() {
            testContainAllHelper("foo bar fizz buzz", new CharSequence[]{"foo", "fu", "baz"}, false);
        }

        @Test
        public void testContainAllWhenValueContainsSomeOthersCharSequence() {
            testContainAllHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("foo"), new StringWrapper("fu"), new StringWrapper("baz")}, false);
        }

        @Test
        public void testContainAllWhenValueContainsSomeOthersCharSequenceWithDifferentCase() {
            testContainAllHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("FOO"), new StringWrapper("FU"), new StringWrapper("BAZ")}, false);
        }

        @Test
        public void testContainAllWhenValueContainsSomeOthersWithDifferentCase() {
            testContainAllHelper("foo bar fizz buzz", new CharSequence[]{"FOO", "FU", "BAZ"}, false);
        }

        @Test
        public void testContainAllWhenValueDoesNotContainOther() {
            testContainAllHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz"}, false);
        }

        @Test
        public void testContainAllWhenValueDoesNotContainOtherCharSequence() {
            testContainAllHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz")}, false);
        }

        @Test
        public void testContainAllWhenValueIsExactMatch() {
            testContainAllHelper("foo bar fizz buzz", new CharSequence[]{"foo", "bar", "foo bar fizz buzz"}, true);
        }

        @Test
        public void testContainAllWhenValueIsEmpty() {
            testContainAllHelper(EMPTY, new CharSequence[]{"foo"}, false);
        }

        @Test
        public void testContainAllWhenValueIsNull() {
            testContainAllHelper(null, new CharSequence[]{EMPTY}, false);
        }

        private void testContainAllHelper(String value, CharSequence[] others, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().containAll(others));

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.CONTAIN_ALL, (Object) others);
        }

        @Test
        public void testContainAllIgnoreCaseWhenNoOthers() {
            testContainAllIgnoreCaseHelper(EMPTY, new CharSequence[0], true);
        }

        @Test
        public void testContainAllIgnoreCaseWhenOtherIsEmpty() {
            testContainAllIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testContainAllIgnoreCaseWhenOtherIsNull() {
            testContainAllIgnoreCaseHelper(EMPTY, new CharSequence[]{EMPTY, null}, false);
        }

        @Test
        public void testContainAllIgnoreCaseWhenOthersIsNull() {
            testContainAllIgnoreCaseHelper(EMPTY, null, true);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueAndOtherAreEmpty() {
            testContainAllIgnoreCaseHelper(EMPTY, new CharSequence[]{EMPTY}, true);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueContainsAllOthers() {
            testContainAllIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"foo", "bar", "fizz"}, true);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueContainsAllOthersCharSequence() {
            testContainAllIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("foo"), new StringWrapper("bar"), new StringWrapper("fizz")}, true);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueContainsAllOthersCharSequenceWithDifferentCase() {
            testContainAllIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("FOO"), new StringWrapper("BAR"), new StringWrapper("FIZZ")}, true);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueContainsAllOthersWithDifferentCase() {
            testContainAllIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"FOO", "BAR", "FIZZ"}, true);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueContainsSomeOthers() {
            testContainAllIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"foo", "fu", "baz"}, false);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueContainsSomeOthersCharSequence() {
            testContainAllIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("foo"), new StringWrapper("fu"), new StringWrapper("baz")}, false);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueContainsSomeOthersCharSequenceWithDifferentCase() {
            testContainAllIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("FOO"), new StringWrapper("FU"), new StringWrapper("BAZ")}, false);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueContainsSomeOthersWithDifferentCase() {
            testContainAllIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"FOO", "FU", "BAZ"}, false);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueDoesNotContainOther() {
            testContainAllIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"fu", "baz"}, false);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueDoesNotContainOtherCharSequence() {
            testContainAllIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{new StringWrapper("fu"), new StringWrapper("baz")}, false);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueIsExactMatch() {
            testContainAllIgnoreCaseHelper("foo bar fizz buzz", new CharSequence[]{"foo", "bar", "foo BAR FIZZ buzz"}, true);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueIsEmpty() {
            testContainAllIgnoreCaseHelper(EMPTY, new CharSequence[]{"foo"}, false);
        }

        @Test
        public void testContainAllIgnoreCaseWhenValueIsNull() {
            testContainAllIgnoreCaseHelper(null, new CharSequence[]{EMPTY}, false);
        }

        private void testContainAllIgnoreCaseHelper(String value, CharSequence[] others, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().containAllIgnoreCase(others));

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.CONTAIN_ALL_IGNORE_CASE, (Object) others);
        }

        @Test
        public void testContainAnyWhenNoOthers() {
            testContainAnyHelper(EMPTY, new CharSequence[0], false);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.CONTAIN_ANY, (Object) others);
        }

        @Test
        public void testContainAnyIgnoreCaseWhenNoOthers() {
            testContainAnyIgnoreCaseHelper(EMPTY, new CharSequence[0], false);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.CONTAIN_ANY_IGNORE_CASE, (Object) others);
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

            verify(getMockVerification()).report(eq(expected), eq(StringVerifier.MessageKeys.CONTAIN_IGNORE_CASE), getArgsCaptor().capture());

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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.EMPTY);
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

            verify(getMockVerification()).report(eq(expected), eq(StringVerifier.MessageKeys.END_WITH), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testEndWithAnyWhenNoOthers() {
            testEndWithAnyHelper(EMPTY, new CharSequence[0], false);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.END_WITH_ANY, (Object) others);
        }

        @Test
        public void testEndWithAnyIgnoreCaseWhenNoOthers() {
            testEndWithAnyIgnoreCaseHelper(EMPTY, new CharSequence[0], false);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.END_WITH_ANY_IGNORE_CASE, (Object) others);
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

            verify(getMockVerification()).report(eq(expected), eq(StringVerifier.MessageKeys.END_WITH_IGNORE_CASE), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testEqualToAnyIgnoreCaseWhenNoOthers() {
            testEqualToAnyIgnoreCaseHelper(EMPTY, new CharSequence[0], false);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.EQUAL_TO_ANY_IGNORE_CASE, (Object) others);
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

            verify(getMockVerification()).report(eq(expected), eq(StringVerifier.MessageKeys.EQUAL_TO_IGNORE_CASE), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.LOWER_CASE);
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
        public void testMatchWhenValueIsEmptyAndNotMatchAndRegexIsCharSequence() {
            testMatchHelper(EMPTY, new StringWrapper("fo{2}"), false);
        }

        @Test
        public void testMatchWhenValueIsMatch() {
            testMatchHelper("foo", "fo{2}", true);
        }

        @Test
        public void testMatchWhenValueIsMatchAndRegexIsCharSequence() {
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

            verify(getMockVerification()).report(eq(expected), eq(StringVerifier.MessageKeys.MATCH), getArgsCaptor().capture());

            assertSame("Passes regex for message formatting", regex, getArgsCaptor().getValue());
        }

        @Test
        public void testMatchAllWhenNoRegularExpressions() {
            testMatchAllHelper("foo", createEmptyArray(CharSequence.class), true);
        }

        @Test
        public void testMatchAllWhenRegularExpressionIsNull() {
            testMatchAllHelper("foo", createArray((CharSequence) null), false);
        }

        @Test
        public void testMatchAllWhenRegularExpressionsIsNull() {
            testMatchAllHelper("foo", null, true);
        }

        @Test
        public void testMatchAllWhenValueMatchesAllRegularExpressions() {
            testMatchAllHelper("foo", createArray("fo{2}", ".*"), true);
        }

        @Test
        public void testMatchAllWhenValueMatchesAllRegularExpressionsWhenNotCharSequences() {
            testMatchAllHelper("foo", createArray(new StringWrapper("fo{2}"), new StringWrapper(".*")), true);
        }

        @Test
        public void testMatchAllWhenValueMatchesSomeRegularExpressions() {
            testMatchAllHelper("foo", createArray("fo{2}", "fiz{2}"), false);
        }

        @Test
        public void testMatchAllWhenValueMatchesSomeRegularExpressionsWhenNotCharSequences() {
            testMatchAllHelper("foo", createArray(new StringWrapper("fo{2}"), new StringWrapper("fiz{2}")), false);
        }

        @Test
        public void testMatchAllWhenValueDoesNotMatchAnyRegularExpression() {
            testMatchAllHelper("foo", createArray("fiz{2}", "buz{2}"), false);
        }

        @Test
        public void testMatchAllWhenValueDoesNotMatchAnyRegularExpressionWhenNotCharSequences() {
            testMatchAllHelper("foo", createArray(new StringWrapper("fiz{2}"), new StringWrapper("buz{2}")), false);
        }

        @Test
        public void testMatchAllWhenValueIsNull() {
            testMatchAllHelper(null, createArray(".*"), false);
        }

        private void testMatchAllHelper(String value, CharSequence[] regexes, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().matchAll(regexes));

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.MATCH_ALL, (Object) regexes);
        }

        @Test
        public void testMatchAnyWhenNoRegularExpressions() {
            testMatchAnyHelper("foo", createEmptyArray(CharSequence.class), false);
        }

        @Test
        public void testMatchAnyWhenRegularExpressionIsNull() {
            testMatchAnyHelper("foo", createArray((CharSequence) null), false);
        }

        @Test
        public void testMatchAnyWhenRegularExpressionsIsNull() {
            testMatchAnyHelper("foo", null, false);
        }

        @Test
        public void testMatchAnyWhenValueMatchesAllRegularExpressions() {
            testMatchAnyHelper("foo", createArray("fo{2}", ".*"), true);
        }

        @Test
        public void testMatchAnyWhenValueMatchesAllRegularExpressionsWhenNotCharSequences() {
            testMatchAnyHelper("foo", createArray(new StringWrapper("fo{2}"), new StringWrapper(".*")), true);
        }

        @Test
        public void testMatchAnyWhenValueMatchesSomeRegularExpressions() {
            testMatchAnyHelper("foo", createArray("fo{2}", "fiz{2}"), true);
        }

        @Test
        public void testMatchAnyWhenValueMatchesSomeRegularExpressionsWhenNotCharSequences() {
            testMatchAnyHelper("foo", createArray(new StringWrapper("fo{2}"), new StringWrapper("fiz{2}")), true);
        }

        @Test
        public void testMatchAnyWhenValueDoesNotMatchAnyRegularExpression() {
            testMatchAnyHelper("foo", createArray("fiz{2}", "buz{2}"), false);
        }

        @Test
        public void testMatchAnyWhenValueDoesNotMatchAnyRegularExpressionWhenNotCharSequences() {
            testMatchAnyHelper("foo", createArray(new StringWrapper("fiz{2}"), new StringWrapper("buz{2}")), false);
        }

        @Test
        public void testMatchAnyWhenValueIsNull() {
            testMatchAnyHelper(null, createArray(".*"), false);
        }

        private void testMatchAnyHelper(String value, CharSequence[] regexes, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().matchAny(regexes));

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.MATCH_ANY, (Object) regexes);
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

            verify(getMockVerification()).report(eq(expected), eq(StringVerifier.MessageKeys.MATCH), getArgsCaptor().capture());

            assertSame("Passes pattern for message formatting", pattern, getArgsCaptor().getValue());
        }

        @Test
        public void testMatchAllWithPatternsWhenNoPatterns() {
            testMatchAllWithPatternsHelper("foo", createEmptyArray(Pattern.class), true);
        }

        @Test
        public void testMatchAllWithPatternsWhenPatternIsNull() {
            testMatchAllWithPatternsHelper("foo", createArray((Pattern) null), false);
        }

        @Test
        public void testMatchAllWithPatternsWhenPatternsIsNull() {
            testMatchAllWithPatternsHelper("foo", null, true);
        }

        @Test
        public void testMatchAllWithPatternsWhenValueMatchesAllPatterns() {
            testMatchAllWithPatternsHelper("foo", createArray(Pattern.compile("fo{2}"), Pattern.compile(".*")), true);
        }

        @Test
        public void testMatchAllWithPatternsWhenValueMatchesSomePatterns() {
            testMatchAllWithPatternsHelper("foo", createArray(Pattern.compile("fo{2}"), Pattern.compile("fiz{2}")), false);
        }

        @Test
        public void testMatchAllWithPatternsWhenValueDoesNotMatchAnyPattern() {
            testMatchAllWithPatternsHelper("foo", createArray(Pattern.compile("fiz{2}"), Pattern.compile("buz{2}")), false);
        }

        @Test
        public void testMatchAllWithPatternsWhenValueIsNull() {
            testMatchAllWithPatternsHelper(null, createArray(Pattern.compile(".*")), false);
        }

        private void testMatchAllWithPatternsHelper(String value, Pattern[] patterns, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().matchAll(patterns));

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.MATCH_ALL, (Object) patterns);
        }

        @Test
        public void testMatchAnyWithPatternsWhenNoPatterns() {
            testMatchAnyWithPatternsHelper("foo", createEmptyArray(Pattern.class), false);
        }

        @Test
        public void testMatchAnyWithPatternsWhenPatternIsNull() {
            testMatchAnyWithPatternsHelper("foo", createArray((Pattern) null), false);
        }

        @Test
        public void testMatchAnyWithPatternsWhenPatternsIsNull() {
            testMatchAnyWithPatternsHelper("foo", null, false);
        }

        @Test
        public void testMatchAnyWithPatternsWhenValueMatchesAllPatterns() {
            testMatchAnyWithPatternsHelper("foo", createArray(Pattern.compile("fo{2}"), Pattern.compile(".*")), true);
        }

        @Test
        public void testMatchAnyWithPatternsWhenValueMatchesSomePatterns() {
            testMatchAnyWithPatternsHelper("foo", createArray(Pattern.compile("fo{2}"), Pattern.compile("fiz{2}")), true);
        }

        @Test
        public void testMatchAnyWithPatternsWhenValueDoesNotMatchAnyPattern() {
            testMatchAnyWithPatternsHelper("foo", createArray(Pattern.compile("fiz{2}"), Pattern.compile("buz{2}")), false);
        }

        @Test
        public void testMatchAnyWithPatternsWhenValueIsNull() {
            testMatchAnyWithPatternsHelper(null, createArray(Pattern.compile(".*")), false);
        }

        private void testMatchAnyWithPatternsHelper(String value, Pattern[] patterns, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().matchAny(patterns));

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.MATCH_ANY, (Object) patterns);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.NUMERIC);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.NUMERIC_SPACE);
        }

        @Test
        public void testSizeOfWhenValueIsEmptyAndLengthIsDifferent() {
            testSizeOfHelper(EMPTY, 1, false);
        }

        @Test
        public void testSizeOfWhenValueIsEmptyAndLengthIsSame() {
            testSizeOfHelper(EMPTY, 0, true);
        }

        @Test
        public void testSizeOfWhenValueIsNotEmptyAndLengthIsDifferent() {
            testSizeOfHelper("foo", 4, false);
        }

        @Test
        public void testSizeOfWhenValueIsNotEmptyAndLengthIsSame() {
            testSizeOfHelper("foo", 3, true);
        }

        @Test
        public void testSizeOfWhenValueIsNullAndLengthIsNotZero() {
            testSizeOfHelper(null, 1, false);
        }

        @Test
        public void testSizeOfWhenValueIsNullAndLengthIsZero() {
            testSizeOfHelper(null, 0, true);
        }

        private void testSizeOfHelper(String value, int size, boolean expected) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().sizeOf(size));

            verify(getMockVerification()).report(eq(expected), eq(StringVerifier.MessageKeys.SIZE_OF), getArgsCaptor().capture());

            assertSame("Passes size for message formatting", size, getArgsCaptor().getValue());
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

            verify(getMockVerification()).report(eq(expected), eq(StringVerifier.MessageKeys.START_WITH), getArgsCaptor().capture());

            assertSame("Passes other for message formatting", other, getArgsCaptor().getValue());
        }

        @Test
        public void testStartWithAnyWhenNoOthers() {
            testStartWithAnyHelper(EMPTY, new CharSequence[0], false);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.START_WITH_ANY, (Object) others);
        }

        @Test
        public void testStartWithAnyIgnoreCaseWhenNoOthers() {
            testStartWithAnyIgnoreCaseHelper(EMPTY, new CharSequence[0], false);
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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.START_WITH_ANY_IGNORE_CASE, (Object) others);
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

            verify(getMockVerification()).report(eq(expected), eq(StringVerifier.MessageKeys.START_WITH_IGNORE_CASE), getArgsCaptor().capture());

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

            verify(getMockVerification()).report(expected, StringVerifier.MessageKeys.UPPER_CASE);
        }

        @Override
        protected StringVerifier createCustomVerifier() {
            return new StringVerifier(getMockVerification());
        }
    }

    public static class StringVerifierMessageKeysTest extends MessageKeyEnumTestCase<StringVerifier.MessageKeys> {

        @Override
        protected Class<? extends Enum> getEnumClass() {
            return StringVerifier.MessageKeys.class;
        }

        @Override
        protected Map<String, String> getMessageKeys() {
            Map<String, String> messageKeys = new HashMap<>();
            messageKeys.put("ALPHA", "io.skelp.verifier.type.StringVerifier.alpha");
            messageKeys.put("ALPHA_SPACE", "io.skelp.verifier.type.StringVerifier.alphaSpace");
            messageKeys.put("ALPHANUMERIC", "io.skelp.verifier.type.StringVerifier.alphanumeric");
            messageKeys.put("ALPHANUMERIC_SPACE", "io.skelp.verifier.type.StringVerifier.alphanumericSpace");
            messageKeys.put("ASCII_PRINTABLE", "io.skelp.verifier.type.StringVerifier.asciiPrintable");
            messageKeys.put("BLANK", "io.skelp.verifier.type.StringVerifier.blank");
            messageKeys.put("CONTAIN", "io.skelp.verifier.type.StringVerifier.contain");
            messageKeys.put("CONTAIN_ALL", "io.skelp.verifier.type.StringVerifier.containAll");
            messageKeys.put("CONTAIN_ALL_IGNORE_CASE", "io.skelp.verifier.type.StringVerifier.containAllIgnoreCase");
            messageKeys.put("CONTAIN_ANY", "io.skelp.verifier.type.StringVerifier.containAny");
            messageKeys.put("CONTAIN_ANY_IGNORE_CASE", "io.skelp.verifier.type.StringVerifier.containAnyIgnoreCase");
            messageKeys.put("CONTAIN_IGNORE_CASE", "io.skelp.verifier.type.StringVerifier.containIgnoreCase");
            messageKeys.put("EMPTY", "io.skelp.verifier.type.StringVerifier.empty");
            messageKeys.put("END_WITH", "io.skelp.verifier.type.StringVerifier.endWith");
            messageKeys.put("END_WITH_ANY", "io.skelp.verifier.type.StringVerifier.endWithAny");
            messageKeys.put("END_WITH_ANY_IGNORE_CASE", "io.skelp.verifier.type.StringVerifier.endWithAnyIgnoreCase");
            messageKeys.put("END_WITH_IGNORE_CASE", "io.skelp.verifier.type.StringVerifier.endWithIgnoreCase");
            messageKeys.put("EQUAL_TO_ANY_IGNORE_CASE", "io.skelp.verifier.type.StringVerifier.equalToAnyIgnoreCase");
            messageKeys.put("EQUAL_TO_IGNORE_CASE", "io.skelp.verifier.type.StringVerifier.equalToIgnoreCase");
            messageKeys.put("LOWER_CASE", "io.skelp.verifier.type.StringVerifier.lowerCase");
            messageKeys.put("MATCH", "io.skelp.verifier.type.StringVerifier.match");
            messageKeys.put("MATCH_ALL", "io.skelp.verifier.type.StringVerifier.matchAll");
            messageKeys.put("MATCH_ANY", "io.skelp.verifier.type.StringVerifier.matchAny");
            messageKeys.put("NUMERIC", "io.skelp.verifier.type.StringVerifier.numeric");
            messageKeys.put("NUMERIC_SPACE", "io.skelp.verifier.type.StringVerifier.numericSpace");
            messageKeys.put("SIZE_OF", "io.skelp.verifier.type.StringVerifier.sizeOf");
            messageKeys.put("START_WITH", "io.skelp.verifier.type.StringVerifier.startWith");
            messageKeys.put("START_WITH_ANY", "io.skelp.verifier.type.StringVerifier.startWithAny");
            messageKeys.put("START_WITH_ANY_IGNORE_CASE", "io.skelp.verifier.type.StringVerifier.startWithAnyIgnoreCase");
            messageKeys.put("START_WITH_IGNORE_CASE", "io.skelp.verifier.type.StringVerifier.startWithIgnoreCase");
            messageKeys.put("UPPER_CASE", "io.skelp.verifier.type.StringVerifier.upperCase");

            return messageKeys;
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
