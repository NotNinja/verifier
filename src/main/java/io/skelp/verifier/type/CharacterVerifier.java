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
     * Verifies that the value is a letter.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).alpha() => FAIL
     * Verifier.verify('\0').alpha()             => FAIL
     * Verifier.verify('0').alpha()              => FAIL
     * Verifier.verify('Z').alpha()              => PASS
     * Verifier.verify('१').alpha()              => FAIL
     * Verifier.verify('É').alpha()              => PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlpha()
     */
    public CharacterVerifier alpha() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && Character.isLetter(value);

        verification().check(result, "be a letter");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a letter or digit.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).alphanumeric() => FAIL
     * Verifier.verify('\0').alphanumeric()             => FAIL
     * Verifier.verify('0').alphanumeric()              => PASS
     * Verifier.verify('Z').alphanumeric()              => PASS
     * Verifier.verify('१').alphanumeric()              => PASS
     * Verifier.verify('É').alphanumeric()              => PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlphanumeric()
     */
    public CharacterVerifier alphanumeric() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && Character.isLetterOrDigit(value);

        verification().check(result, "be a letter or digit");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is ASCII.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).ascii() => FAIL
     * Verifier.verify('\0').ascii()             => PASS
     * Verifier.verify('0').ascii()              => PASS
     * Verifier.verify('Z').ascii()              => PASS
     * Verifier.verify('१').ascii()              => FAIL
     * Verifier.verify('É').ascii()              => FAIL
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
     * Verifier.verify('\0').asciiAlpha()             => FAIL
     * Verifier.verify('0').asciiAlpha()              => FAIL
     * Verifier.verify('Z').asciiAlpha()              => PASS
     * Verifier.verify('१').asciiAlpha()              => FAIL
     * Verifier.verify('É').asciiAlpha()              => FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #alpha()
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
     * Verifier.verify('\0').asciiAlphaLowerCase()             => FAIL
     * Verifier.verify('0').asciiAlphaLowerCase()              => FAIL
     * Verifier.verify('a').asciiAlphaLowerCase()              => PASS
     * Verifier.verify('Z').asciiAlphaLowerCase()              => FAIL
     * Verifier.verify('१').asciiAlphaLowerCase()              => FAIL
     * Verifier.verify('é').asciiAlphaLowerCase()              => FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlphaUpperCase()
     * @see #lowerCase()
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
     * Verifier.verify('\0').asciiAlphaUpperCase()             => FAIL
     * Verifier.verify('0').asciiAlphaUpperCase()              => FAIL
     * Verifier.verify('a').asciiAlphaUpperCase()              => FAIL
     * Verifier.verify('Z').asciiAlphaUpperCase()              => PASS
     * Verifier.verify('१').asciiAlphaUpperCase()              => FAIL
     * Verifier.verify('É').asciiAlphaUpperCase()              => FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlphaLowerCase()
     * @see #upperCase()
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
     * Verifier.verify('\0').asciiAlphanumeric()             => FAIL
     * Verifier.verify('0').asciiAlphanumeric()              => PASS
     * Verifier.verify('Z').asciiAlphanumeric()              => PASS
     * Verifier.verify('१').asciiAlphanumeric()              => FAIL
     * Verifier.verify('É').asciiAlphanumeric()              => FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #alphanumeric()
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
     * Verifier.verify('\0').asciiControl()             => PASS
     * Verifier.verify('0').asciiControl()              => FAIL
     * Verifier.verify('Z').asciiControl()              => FAIL
     * Verifier.verify('१').asciiControl()              => FAIL
     * Verifier.verify('É').asciiControl()              => FAIL
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
     * Verifier.verify('\0').asciiNumeric()             => FAIL
     * Verifier.verify('0').asciiNumeric()              => PASS
     * Verifier.verify('Z').asciiNumeric()              => FAIL
     * Verifier.verify('१').asciiNumeric()              => FAIL
     * Verifier.verify('É').asciiNumeric()              => FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #numeric()
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
     * Verifier.verify('\0').asciiPrintable()             => FAIL
     * Verifier.verify('0').asciiPrintable()              => PASS
     * Verifier.verify('Z').asciiPrintable()              => PASS
     * Verifier.verify(' ').asciiPrintable()              => PASS
     * Verifier.verify('~').asciiPrintable()              => PASS
     * Verifier.verify('१').asciiPrintable()              => FAIL
     * Verifier.verify('É').asciiPrintable()              => FAIL
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

    /**
     * <p>
     * Verifies that the value is a lower case letter.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).lowerCase() => FAIL
     * Verifier.verify('\0').lowerCase()             => FAIL
     * Verifier.verify('0').lowerCase()              => FAIL
     * Verifier.verify('a').lowerCase()              => PASS
     * Verifier.verify('Z').lowerCase()              => FAIL
     * Verifier.verify('१').lowerCase()              => FAIL
     * Verifier.verify('é').lowerCase()              => PASS
     * Verifier.verify('É').lowerCase()              => FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlphaLowerCase()
     * @see #upperCase()
     */
    public CharacterVerifier lowerCase() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && Character.isLowerCase(value);

        verification().check(result, "be lower case");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a digit.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).numeric() => FAIL
     * Verifier.verify('\0').numeric()             => FAIL
     * Verifier.verify('0').numeric()              => PASS
     * Verifier.verify('Z').numeric()              => FAIL
     * Verifier.verify('१').numeric()              => PASS
     * Verifier.verify('É').numeric()              => FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiNumeric()
     */
    public CharacterVerifier numeric() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && Character.isDigit(value);

        verification().check(result, "be a digit");

        return this;
    }

    @Override
    public CharacterVerifier truthy() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && value == '1';

        verification().check(result, TRUTHY_MESSAGE);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an upper case letter.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).upperCase() => FAIL
     * Verifier.verify('\0').upperCase()             => FAIL
     * Verifier.verify('0').upperCase()              => FAIL
     * Verifier.verify('a').upperCase()              => FAIL
     * Verifier.verify('Z').upperCase()              => PASS
     * Verifier.verify('१').upperCase()              => FAIL
     * Verifier.verify('é').upperCase()              => FAIL
     * Verifier.verify('É').upperCase()              => PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlphaUpperCase()
     * @see #lowerCase()
     */
    public CharacterVerifier upperCase() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && Character.isUpperCase(value);

        verification().check(result, "be upper case");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is whitespace.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).whitespace() => FAIL
     * Verifier.verify('\0').whitespace()             => FAIL
     * Verifier.verify('0').whitespace()              => FAIL
     * Verifier.verify('Z').whitespace()              => FAIL
     * Verifier.verify(' ').whitespace()              => PASS
     * Verifier.verify('\r').whitespace()             => PASS
     * Verifier.verify('\n').whitespace()             => PASS
     * Verifier.verify('\t').whitespace()             => PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public CharacterVerifier whitespace() throws VerifierException {
        final Character value = verification().getValue();
        final boolean result = value != null && Character.isWhitespace(value);

        verification().check(result, "be whitespace");

        return this;
    }
}
