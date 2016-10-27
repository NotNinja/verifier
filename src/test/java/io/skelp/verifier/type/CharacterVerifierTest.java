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

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import io.skelp.verifier.AbstractCustomVerifierTestCase;
import io.skelp.verifier.CustomVerifierTestCaseBase;
import io.skelp.verifier.type.base.BaseComparableVerifierTestCase;
import io.skelp.verifier.type.base.BaseTruthVerifierTestCase;
import io.skelp.verifier.util.Function;

/**
 * <p>
 * Tests for the {@link CharacterVerifier} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
@RunWith(Enclosed.class)
public class CharacterVerifierTest {

    public static class CharacterVerifierAbstractCustomVerifierTest extends AbstractCustomVerifierTestCase<Character, CharacterVerifier> {

        @Override
        protected CharacterVerifier createCustomVerifier() {
            return new CharacterVerifier(getMockVerification());
        }

        @Override
        protected Character createValueOne() {
            return new Character('a');
        }

        @Override
        protected Character createValueTwo() {
            return new Character('z');
        }

        @Override
        protected Class<?> getParentClass() {
            return Object.class;
        }

        @Override
        protected Class<?> getValueClass() {
            return Character.class;
        }
    }

    public static class CharacterVerifierBaseComparableVerifierTest extends BaseComparableVerifierTestCase<Character, CharacterVerifier> {

        @Override
        protected CharacterVerifier createCustomVerifier() {
            return new CharacterVerifier(getMockVerification());
        }

        @Override
        protected Character getBaseValue() {
            return 'm';
        }

        @Override
        protected Character getHigherValue() {
            return 't';
        }

        @Override
        protected Character getHighestValue() {
            return 'z';
        }

        @Override
        protected Character getLowerValue() {
            return 'f';
        }

        @Override
        protected Character getLowestValue() {
            return 'a';
        }
    }

    public static class CharacterVerifierBaseTruthVerifierTest extends BaseTruthVerifierTestCase<Character, CharacterVerifier> {

        @Override
        protected CharacterVerifier createCustomVerifier() {
            return new CharacterVerifier(getMockVerification());
        }

        @Override
        protected Character[] getFalsyValues() {
            return new Character[]{'0'};
        }

        @Override
        protected Character[] getTruthyValues() {
            return new Character[]{'1'};
        }
    }

    public static class CharacterVerifierMiscTest extends CustomVerifierTestCaseBase<Character, CharacterVerifier> {

        private static Character[] asciiControls;
        private static Character[] asciiLowerCaseLetters;
        private static Character[] asciiNumbers;
        private static Character[] asciiOtherPrintables;
        private static Character[] asciiUpperCaseLetters;
        private static Character[] extendedAsciiCharacters;

        @BeforeClass
        public static void setUpClass() {
            asciiControls = getAsciiCharacters(new Function<Boolean, Integer>() {
                @Override
                public Boolean apply(Integer input) {
                    return input < 32 || input == 127;
                }
            });
            asciiLowerCaseLetters = getAsciiCharacters(new Function<Boolean, Integer>() {
                @Override
                public Boolean apply(Integer input) {
                    return input > 96 && input < 123;
                }
            });
            asciiNumbers = getAsciiCharacters(new Function<Boolean, Integer>() {
                @Override
                public Boolean apply(Integer input) {
                    return input > 47 && input < 58;
                }
            });
            asciiOtherPrintables = getAsciiCharacters(new Function<Boolean, Integer>() {
                @Override
                public Boolean apply(Integer input) {
                    return (input > 31 && input < 48) || (input > 57 && input < 65) || (input > 90 && input < 97) || (input > 122 && input < 127);
                }
            });
            asciiUpperCaseLetters = getAsciiCharacters(new Function<Boolean, Integer>() {
                @Override
                public Boolean apply(Integer input) {
                    return input > 64 && input < 91;
                }
            });
            extendedAsciiCharacters = getExtendedAsciiCharacters();
        }

        private static Character[] getAsciiCharacters(Function<Boolean, Integer> matcher) {
            List<Character> characters = new ArrayList<>();
            for (int i = 0; i < 128; i++) {
                if (matcher.apply(i)) {
                    characters.add((char) i);
                }
            }

            return characters.toArray(new Character[0]);
        }

        private static Character[] getExtendedAsciiCharacters() {
            List<Character> characters = new ArrayList<>();
            for (char ch = 128; ch < 256; ch++) {
                characters.add(ch);
            }

            return characters.toArray(new Character[0]);
        }

        @Test
        public void testAsciiWhenValueIsAsciiControl() {
            testAsciiHelper(asciiControls, true);
        }

        @Test
        public void testAsciiWhenValueIsAsciiLowerCaseLetter() {
            testAsciiHelper(asciiLowerCaseLetters, true);
        }

        @Test
        public void testAsciiWhenValueIsAsciiNumber() {
            testAsciiHelper(asciiNumbers, true);
        }

        @Test
        public void testAsciiWhenValueIsAsciiUpperCaseLetter() {
            testAsciiHelper(asciiUpperCaseLetters, true);
        }

        @Test
        public void testAsciiWhenValueIsNotAscii() {
            testAsciiHelper(extendedAsciiCharacters, false);
        }

        @Test
        public void testAsciiWhenValueIsNull() {
            testAsciiHelper(new Character[]{null}, false);
        }

        private void testAsciiHelper(Character[] values, boolean expected) {
            for (Character value : values) {
                setValue(value);

                assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().ascii());
            }

            verify(getMockVerification(), times(values.length)).check(expected, "be ASCII");
        }

        @Test
        public void testAsciiAlphaWhenValueIsAsciiControl() {
            testAsciiAlphaHelper(asciiControls, false);
        }

        @Test
        public void testAsciiAlphaWhenValueIsAsciiLowerCaseLetter() {
            testAsciiAlphaHelper(asciiLowerCaseLetters, true);
        }

        @Test
        public void testAsciiAlphaWhenValueIsAsciiNumber() {
            testAsciiAlphaHelper(asciiNumbers, false);
        }

        @Test
        public void testAsciiAlphaWhenValueIsAsciiUpperCaseLetter() {
            testAsciiAlphaHelper(asciiUpperCaseLetters, true);
        }

        @Test
        public void testAsciiAlphaWhenValueIsNotAscii() {
            testAsciiAlphaHelper(extendedAsciiCharacters, false);
        }

        @Test
        public void testAsciiAlphaWhenValueIsNull() {
            testAsciiAlphaHelper(new Character[]{null}, false);
        }

        private void testAsciiAlphaHelper(Character[] values, boolean expected) {
            for (Character value : values) {
                setValue(value);

                assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().asciiAlpha());
            }

            verify(getMockVerification(), times(values.length)).check(expected, "be an ASCII letter");
        }

        @Test
        public void testAsciiAlphaLowerCaseWhenValueIsAsciiControl() {
            testAsciiAlphaLowerCaseHelper(asciiControls, false);
        }

        @Test
        public void testAsciiAlphaLowerCaseWhenValueIsAsciiLowerCaseLetter() {
            testAsciiAlphaLowerCaseHelper(asciiLowerCaseLetters, true);
        }

        @Test
        public void testAsciiAlphaLowerCaseWhenValueIsAsciiNumber() {
            testAsciiAlphaLowerCaseHelper(asciiNumbers, false);
        }

        @Test
        public void testAsciiAlphaLowerCaseWhenValueIsAsciiUpperCaseLetter() {
            testAsciiAlphaLowerCaseHelper(asciiUpperCaseLetters, false);
        }

        @Test
        public void testAsciiAlphaLowerCaseWhenValueIsNotAscii() {
            testAsciiAlphaLowerCaseHelper(extendedAsciiCharacters, false);
        }

        @Test
        public void testAsciiAlphaLowerCaseWhenValueIsNull() {
            testAsciiAlphaLowerCaseHelper(new Character[]{null}, false);
        }

        private void testAsciiAlphaLowerCaseHelper(Character[] values, boolean expected) {
            for (Character value : values) {
                setValue(value);

                assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().asciiAlphaLowerCase());
            }

            verify(getMockVerification(), times(values.length)).check(expected, "be an ASCII lower case letter");
        }

        @Test
        public void testAsciiAlphaUpperCaseWhenValueIsAsciiControl() {
            testAsciiAlphaUpperCaseHelper(asciiControls, false);
        }

        @Test
        public void testAsciiAlphaUpperCaseWhenValueIsAsciiLowerCaseLetter() {
            testAsciiAlphaUpperCaseHelper(asciiLowerCaseLetters, false);
        }

        @Test
        public void testAsciiAlphaUpperCaseWhenValueIsAsciiNumber() {
            testAsciiAlphaUpperCaseHelper(asciiNumbers, false);
        }

        @Test
        public void testAsciiAlphaUpperCaseWhenValueIsAsciiUpperCaseLetter() {
            testAsciiAlphaUpperCaseHelper(asciiUpperCaseLetters, true);
        }

        @Test
        public void testAsciiAlphaUpperCaseWhenValueIsNotAscii() {
            testAsciiAlphaUpperCaseHelper(extendedAsciiCharacters, false);
        }

        @Test
        public void testAsciiAlphaUpperCaseWhenValueIsNull() {
            testAsciiAlphaUpperCaseHelper(new Character[]{null}, false);
        }

        private void testAsciiAlphaUpperCaseHelper(Character[] values, boolean expected) {
            for (Character value : values) {
                setValue(value);

                assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().asciiAlphaUpperCase());
            }

            verify(getMockVerification(), times(values.length)).check(expected, "be an ASCII upper case letter");
        }

        @Test
        public void testAsciiAlphanumericWhenValueIsAsciiControl() {
            testAsciiAlphanumericHelper(asciiControls, false);
        }

        @Test
        public void testAsciiAlphanumericWhenValueIsAsciiLowerCaseLetter() {
            testAsciiAlphanumericHelper(asciiLowerCaseLetters, true);
        }

        @Test
        public void testAsciiAlphanumericWhenValueIsAsciiNumber() {
            testAsciiAlphanumericHelper(asciiNumbers, true);
        }

        @Test
        public void testAsciiAlphanumericWhenValueIsAsciiUpperCaseLetter() {
            testAsciiAlphanumericHelper(asciiUpperCaseLetters, true);
        }

        @Test
        public void testAsciiAlphanumericWhenValueIsNotAscii() {
            testAsciiAlphanumericHelper(extendedAsciiCharacters, false);
        }

        @Test
        public void testAsciiAlphanumericWhenValueIsNull() {
            testAsciiAlphanumericHelper(new Character[]{null}, false);
        }

        private void testAsciiAlphanumericHelper(Character[] values, boolean expected) {
            for (Character value : values) {
                setValue(value);

                assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().asciiAlphanumeric());
            }

            verify(getMockVerification(), times(values.length)).check(expected, "be an ASCII letter or digit");
        }

        @Test
        public void testAsciiControlWhenValueIsAsciiControl() {
            testAsciiControlHelper(asciiControls, true);
        }

        @Test
        public void testAsciiControlWhenValueIsAsciiLowerCaseLetter() {
            testAsciiControlHelper(asciiLowerCaseLetters, false);
        }

        @Test
        public void testAsciiControlWhenValueIsAsciiNumber() {
            testAsciiControlHelper(asciiNumbers, false);
        }

        @Test
        public void testAsciiControlWhenValueIsAsciiUpperCaseLetter() {
            testAsciiControlHelper(asciiUpperCaseLetters, false);
        }

        @Test
        public void testAsciiControlWhenValueIsNotAscii() {
            testAsciiControlHelper(extendedAsciiCharacters, false);
        }

        @Test
        public void testAsciiControlWhenValueIsNull() {
            testAsciiControlHelper(new Character[]{null}, false);
        }

        private void testAsciiControlHelper(Character[] values, boolean expected) {
            for (Character value : values) {
                setValue(value);

                assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().asciiControl());
            }

            verify(getMockVerification(), times(values.length)).check(expected, "be an ASCII control");
        }

        @Test
        public void testAsciiNumericWhenValueIsAsciiControl() {
            testAsciiNumericHelper(asciiControls, false);
        }

        @Test
        public void testAsciiNumericWhenValueIsAsciiLowerCaseLetter() {
            testAsciiNumericHelper(asciiLowerCaseLetters, false);
        }

        @Test
        public void testAsciiNumericWhenValueIsAsciiNumber() {
            testAsciiNumericHelper(asciiNumbers, true);
        }

        @Test
        public void testAsciiNumericWhenValueIsAsciiUpperCaseLetter() {
            testAsciiNumericHelper(asciiUpperCaseLetters, false);
        }

        @Test
        public void testAsciiNumericWhenValueIsNotAscii() {
            testAsciiNumericHelper(extendedAsciiCharacters, false);
        }

        @Test
        public void testAsciiNumericWhenValueIsNull() {
            testAsciiNumericHelper(new Character[]{null}, false);
        }

        private void testAsciiNumericHelper(Character[] values, boolean expected) {
            for (Character value : values) {
                setValue(value);

                assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().asciiNumeric());
            }

            verify(getMockVerification(), times(values.length)).check(expected, "be an ASCII digit");
        }

        @Test
        public void testAsciiPrintableWhenValueIsAsciiControl() {
            testAsciiPrintableHelper(asciiControls, false);
        }

        @Test
        public void testAsciiPrintableWhenValueIsAsciiLowerCaseLetter() {
            testAsciiPrintableHelper(asciiLowerCaseLetters, true);
        }

        @Test
        public void testAsciiPrintableWhenValueIsAsciiNumber() {
            testAsciiPrintableHelper(asciiNumbers, true);
        }

        @Test
        public void testAsciiPrintableWhenValueIsAsciiUpperCaseLetter() {
            testAsciiPrintableHelper(asciiUpperCaseLetters, true);
        }

        @Test
        public void testAsciiPrintableWhenValueIsOtherAsciiPrintable() {
            testAsciiPrintableHelper(asciiOtherPrintables, true);
        }

        @Test
        public void testAsciiPrintableWhenValueIsNotAscii() {
            testAsciiPrintableHelper(extendedAsciiCharacters, false);
        }

        @Test
        public void testAsciiPrintableWhenValueIsNull() {
            testAsciiPrintableHelper(new Character[]{null}, false);
        }

        private void testAsciiPrintableHelper(Character[] values, boolean expected) {
            for (Character value : values) {
                setValue(value);

                assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().asciiPrintable());
            }

            verify(getMockVerification(), times(values.length)).check(expected, "be ASCII printable");
        }

        @Override
        protected CharacterVerifier createCustomVerifier() {
            return new CharacterVerifier(getMockVerification());
        }
    }
}
