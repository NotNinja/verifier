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
 * <p>
 * An implementation of {@link BaseComparableVerifier} and {@link BaseTruthVerifier} which can be used to verify a
 * {@code Character} value.
 * </p>
 * <p>
 * All of the {@link BaseTruthVerifier} methods are implemented so that {@literal null} and {@literal '0'} are
 * <b>always</b> considered to be falsy and {@literal '1'} is <b>always</b> considered to be truthy.
 * </p>
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
     * <p>
     * Creates an instance of {@link CharacterVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public CharacterVerifier(final Verification<Character> verification) {
        super(verification);
    }

    /**
     * <p>
     * Verifies that the value is ASCII.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).ascii() => FAIL
     * Verifier.verify('æ').ascii()              => FAIL
     * Verifier.verify('\0').ascii()             => PASS
     * Verifier.verify('0').ascii()              => PASS
     * Verifier.verify('Z').ascii()              => PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public CharacterVerifier ascii() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && value < 128;

        verification().check(result, "be ASCII");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an ASCII letter.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiAlpha() => FAIL
     * Verifier.verify('æ').asciiAlpha()              => FAIL
     * Verifier.verify('\0').asciiAlpha()             => FAIL
     * Verifier.verify('0').asciiAlpha()              => FAIL
     * Verifier.verify('Z').asciiAlpha()              => PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public CharacterVerifier asciiAlpha() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && isAsciiAlpha(value);

        verification().check(result, "be an ASCII letter");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an ASCII lower case letter.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiAlphaLowerCase() => FAIL
     * Verifier.verify('æ').asciiAlphaLowerCase()              => FAIL
     * Verifier.verify('\0').asciiAlphaLowerCase()             => FAIL
     * Verifier.verify('0').asciiAlphaLowerCase()              => FAIL
     * Verifier.verify('a').asciiAlphaLowerCase()              => PASS
     * Verifier.verify('Z').asciiAlphaLowerCase()              => FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlphaUpperCase()
     */
    public CharacterVerifier asciiAlphaLowerCase() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && isAsciiAlphaLowerCase(value);

        verification().check(result, "be an ASCII lower case letter");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an ASCII upper case letter.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiAlphaUpperCase() => FAIL
     * Verifier.verify('æ').asciiAlphaUpperCase()              => FAIL
     * Verifier.verify('\0').asciiAlphaUpperCase()             => FAIL
     * Verifier.verify('0').asciiAlphaUpperCase()              => FAIL
     * Verifier.verify('a').asciiAlphaUpperCase()              => FAIL
     * Verifier.verify('Z').asciiAlphaUpperCase()              => PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlphaLowerCase()
     */
    public CharacterVerifier asciiAlphaUpperCase() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && isAsciiAlphaUpperCase(value);

        verification().check(result, "be an ASCII upper case letter");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an ASCII letter or digit.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiAlphanumeric() => FAIL
     * Verifier.verify('æ').asciiAlphanumeric()              => FAIL
     * Verifier.verify('\0').asciiAlphanumeric()             => FAIL
     * Verifier.verify('0').asciiAlphanumeric()              => PASS
     * Verifier.verify('Z').asciiAlphanumeric()              => PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public CharacterVerifier asciiAlphanumeric() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && (isAsciiAlpha(value) || isAsciiNumeric(value));

        verification().check(result, "be an ASCII letter or digit");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an ASCII control.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiControl() => FAIL
     * Verifier.verify('æ').asciiControl()              => FAIL
     * Verifier.verify('\0').asciiControl()             => PASS
     * Verifier.verify('0').asciiControl()              => FAIL
     * Verifier.verify('Z').asciiControl()              => FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public CharacterVerifier asciiControl() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && (value < 32 || value == 127);

        verification().check(result, "be an ASCII control");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an ASCII digit.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiNumeric() => FAIL
     * Verifier.verify('æ').asciiNumeric()              => FAIL
     * Verifier.verify('\0').asciiNumeric()             => FAIL
     * Verifier.verify('0').asciiNumeric()              => PASS
     * Verifier.verify('Z').asciiNumeric()              => FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public CharacterVerifier asciiNumeric() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && isAsciiNumeric(value);

        verification().check(result, "be an ASCII digit");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is ASCII printable.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiPrintable() => FAIL
     * Verifier.verify('æ').asciiPrintable()              => FAIL
     * Verifier.verify('\0').asciiPrintable()             => FAIL
     * Verifier.verify('0').asciiPrintable()              => PASS
     * Verifier.verify('Z').asciiPrintable()              => PASS
     * Verifier.verify(' ').asciiPrintable()              => PASS
     * Verifier.verify('~').asciiPrintable()              => PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public CharacterVerifier asciiPrintable() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && value >= 32 && value < 127;

        verification().check(result, "be ASCII printable");

        return this;
    }

    @Override
    public CharacterVerifier falsy() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value == null || value == '0';

        verification().check(result, FALSY_MESSAGE);

        return this;
    }

    @Override
    public CharacterVerifier truthy() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && value == '1';

        verification().check(result, TRUTHY_MESSAGE);

        return this;
    }
}
