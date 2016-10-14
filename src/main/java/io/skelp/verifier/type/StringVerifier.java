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

import java.util.regex.Pattern;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.type.base.BaseComparableVerifier;
import io.skelp.verifier.type.base.BaseTruthVerifier;
import io.skelp.verifier.util.Function;
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
 *
 * @author Alasdair Mercer
 */
public final class StringVerifier extends BaseComparableVerifier<String, StringVerifier> implements BaseTruthVerifier<String, StringVerifier> {

    private static boolean matchCharacters(final String value, final Function<Boolean, Character> matcher) {
        if (value == null) {
            return false;
        }

        final int length = value.length();
        for (int i = 0; i < length; i++) {
            if (!matcher.apply(value.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static boolean regionMatches(final String value, final boolean ignoreCase, final int offset, final CharSequence charSequence, final int start, final int length) {
        if (value == null) {
            return false;
        }

        if (charSequence instanceof String) {
            return value.regionMatches(ignoreCase, offset, (String) charSequence, start, length);
        }

        int index1 = offset;
        int index2 = start;
        int tempLength = length;

        while (tempLength-- > 0) {
            final char ch1 = value.charAt(index1++);
            final char ch2 = charSequence.charAt(index2++);

            if (ch1 == ch2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            if (Character.toUpperCase(ch1) != Character.toUpperCase(ch2) && Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
                return false;
            }
        }

        return true;
    }

    /**
     * TODO: Document
     *
     * @param verification
     */
    public StringVerifier(final Verification<String> verification) {
        super(verification);
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public StringVerifier alpha() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isLetter(character);
            }
        });

        verification.check(result, "contain only letters");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public StringVerifier alphaSpace() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isLetter(character) || character == ' ';
            }
        });

        verification.check(result, "contain only letters or space");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public StringVerifier alphanumeric() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isLetterOrDigit(character);
            }
        });

        verification.check(result, "contain only letters or digits");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public StringVerifier alphanumericSpace() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isLetterOrDigit(character) || character == ' ';
            }
        });

        verification.check(result, "contain only letters or digits or space");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public StringVerifier asciiPrintable() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return character >= 32 && character < 127;
            }
        });

        verification.check(result, "contain only ASCII printable characters");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public StringVerifier blank() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = value == null || value.trim().isEmpty();

        verification.check(result, "be blank");

        return this;
    }

    /**
     * TODO: Document
     *
     * @param charSequence
     * @return
     * @throws VerifierException
     */
    public StringVerifier contain(final CharSequence charSequence) throws VerifierException {
        final String value = verification.getValue();
        final boolean result = value != null && value.contains(charSequence);

        verification.check(result, "contain '%s'", charSequence);

        return this;
    }

    /**
     * TODO: Document
     *
     * @param charSequence
     * @return
     * @throws VerifierException
     */
    public StringVerifier containIgnoreCase(final CharSequence charSequence) throws VerifierException {
        final String value = verification.getValue();
        boolean result = false;
        if (value != null) {
            final int length = charSequence.length();
            final int maximum = value.length() - length;
            for (int i = 0; i <= maximum; i++) {
                if (regionMatches(value, true, i, charSequence, 0, length)) {
                    result = true;
                    break;
                }
            }
        }

        verification.check(result, "contain '%s' (ignore case)", charSequence);

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public StringVerifier empty() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = value == null || value.isEmpty();

        verification.check(result, "be empty");

        return this;
    }

    /**
     * TODO: Document
     *
     * @param charSequence
     * @return
     * @throws VerifierException
     */
    public StringVerifier endWith(final CharSequence charSequence) throws VerifierException {
        final String value = verification.getValue();
        final boolean result = regionMatches(value, false, value.length() - charSequence.length(), charSequence, 0, charSequence.length());

        verification.check(result, "end with '%s'", charSequence);

        return this;
    }

    /**
     * TODO: Document
     *
     * @param charSequence
     * @return
     * @throws VerifierException
     */
    public StringVerifier endWithIgnoreCase(final CharSequence charSequence) throws VerifierException {
        final String value = verification.getValue();
        final boolean result = regionMatches(value, true, value.length() - charSequence.length(), charSequence, 0, charSequence.length());

        verification.check(result, "end with '%s' (ignore case)", charSequence);

        return this;
    }

    /**
     * TODO: Document
     *
     * @param other
     * @return
     * @throws VerifierException
     */
    public StringVerifier equalToIgnoreCase(final CharSequence other) throws VerifierException {
        final String value = verification.getValue();
        final boolean result = other == null ? value == null : regionMatches(value, true, 0, other, 0, value.length());

        verification.check(result, "be equal to '%s' (ignore case)", other);

        return this;
    }

    @Override
    public StringVerifier falsehood() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = Boolean.FALSE.toString().equalsIgnoreCase(value);

        verification.check(result, "be false");

        return this;
    }

    /**
     * TODO: Document
     *
     * @param length
     * @return
     * @throws VerifierException
     */
    public StringVerifier length(final int length) throws VerifierException {
        final String value = verification.getValue();
        final boolean result = value != null && value.length() == length;

        verification.check(result, "have length of '%d'", length);

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public StringVerifier lowerCase() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isLowerCase(character);
            }
        });

        verification.check(result, "be all lower case");

        return this;
    }

    /**
     * TODO: Document
     *
     * @param charSequence
     * @return
     * @throws VerifierException
     */
    public StringVerifier match(final CharSequence charSequence) throws VerifierException {
        final String value = verification.getValue();
        final boolean result = value != null && value.matches(charSequence.toString());

        verification.check(result, "match '%s'", charSequence);

        return this;
    }

    /**
     * TODO: Document
     *
     * @param pattern
     * @return
     * @throws VerifierException
     */
    public StringVerifier match(final Pattern pattern) throws VerifierException {
        final String value = verification.getValue();
        final boolean result = value != null && pattern.matcher(value).matches();

        verification.check(result, "match '%s'", pattern);

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public StringVerifier numeric() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isDigit(character);
            }
        });

        verification.check(result, "contain only digits");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public StringVerifier numericSpace() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isDigit(character) || character == ' ';
            }
        });

        verification.check(result, "contain only digits or space");

        return this;
    }

    /**
     * TODO: Document
     *
     * @param charSequence
     * @return
     * @throws VerifierException
     */
    public StringVerifier startWith(final CharSequence charSequence) throws VerifierException {
        final String value = verification.getValue();
        final boolean result = regionMatches(value, false, 0, charSequence, 0, charSequence.length());

        verification.check(result, "start with '%s'", charSequence);

        return this;
    }

    /**
     * TODO: Document
     *
     * @param charSequence
     * @return
     * @throws VerifierException
     */
    public StringVerifier startWithIgnoreCase(final CharSequence charSequence) throws VerifierException {
        final String value = verification.getValue();
        final boolean result = regionMatches(value, true, 0, charSequence, 0, charSequence.length());

        verification.check(result, "start with '%s' (ignore case)", charSequence);

        return this;
    }

    @Override
    public StringVerifier truth() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = Boolean.TRUE.toString().equalsIgnoreCase(value);

        verification.check(result, "be true");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public StringVerifier upperCase() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isUpperCase(character);
            }
        });

        verification.check(result, "be all upper case");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public StringVerifier whitespace() throws VerifierException {
        final String value = verification.getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isWhitespace(character);
            }
        });

        verification.check(result, "contain only whitespace");

        return this;
    }
}
