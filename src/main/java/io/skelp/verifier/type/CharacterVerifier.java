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

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.type.base.BaseComparableVerifier;
import io.skelp.verifier.type.base.BaseTruthVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
 *
 * @author Alasdair Mercer
 */
public final class CharacterVerifier extends BaseComparableVerifier<Character, CharacterVerifier> implements BaseTruthVerifier<Character, CharacterVerifier> {

    private static boolean isAsciiAlpha(final char ch) {
        return isAsciiAlphaUpperCase(ch) || isAsciiAlphaLowerCase(ch);
    }

    private static boolean isAsciiAlphaLowerCase(final char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    private static boolean isAsciiAlphaUpperCase(final char ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    private static boolean isAsciiNumeric(final char ch) {
        return ch >= '0' && ch <= '9';
    }

    /**
     * TODO: Document
     *
     * @param verification
     */
    public CharacterVerifier(final Verification<Character> verification) {
        super(verification);
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public CharacterVerifier ascii() throws VerifierException {
        final Character value = verification.getValue();
        final boolean result = value != null && value < 128;

        verification.check(result, "be ASCII");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public CharacterVerifier asciiAlpha() throws VerifierException {
        final Character value = verification.getValue();
        final boolean result = value != null && isAsciiAlpha(value);

        verification.check(result, "be an ASCII letter");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public CharacterVerifier asciiAlphaLowerCase() throws VerifierException {
        final Character value = verification.getValue();
        final boolean result = value != null && isAsciiAlphaLowerCase(value);

        verification.check(result, "be an ASCII lower case letter");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public CharacterVerifier asciiAlphaUpperCase() throws VerifierException {
        final Character value = verification.getValue();
        final boolean result = value != null && isAsciiAlphaUpperCase(value);

        verification.check(result, "be an ASCII upper case letter");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public CharacterVerifier asciiAlphanumeric() throws VerifierException {
        final Character value = verification.getValue();
        final boolean result = value != null && (isAsciiAlpha(value) || isAsciiNumeric(value));

        verification.check(result, "be an ASCII letter or digit");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public CharacterVerifier asciiControl() throws VerifierException {
        final Character value = verification.getValue();
        final boolean result = value != null && (value < 32 || value == 127);

        verification.check(result, "be an ASCII control");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public CharacterVerifier asciiNumeric() throws VerifierException {
        final Character value = verification.getValue();
        final boolean result = value != null && isAsciiNumeric(value);

        verification.check(result, "be an ASCII digit");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public CharacterVerifier asciiPrintable() throws VerifierException {
        final Character value = verification.getValue();
        final boolean result = value != null && value >= 32 && value < 127;

        verification.check(result, "be ASCII printable");

        return this;
    }

    @Override
    public CharacterVerifier falsehood() throws VerifierException {
        final Character value = verification.getValue();
        final boolean result = value != null && value == '0';

        verification.check(result, "be false");

        return this;
    }

    @Override
    public CharacterVerifier truth() throws VerifierException {
        final Character value = verification.getValue();
        final boolean result = value != null && value == '1';

        verification.check(result, "be true");

        return this;
    }
}
