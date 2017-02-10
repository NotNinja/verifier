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

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.message.MessageKey;
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
     * Verifier.verify((Character) null).alpha() =&gt; FAIL
     * Verifier.verify('\0').alpha()             =&gt; FAIL
     * Verifier.verify('0').alpha()              =&gt; FAIL
     * Verifier.verify('Z').alpha()              =&gt; PASS
     * Verifier.verify('१').alpha()              =&gt; FAIL
     * Verifier.verify('É').alpha()              =&gt; PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlpha()
     */
    public CharacterVerifier alpha() {
        final Character value = verification().getValue();
        final boolean result = value != null && Character.isLetter(value);

        verification().report(result, MessageKeys.ALPHA);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a letter or digit.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).alphanumeric() =&gt; FAIL
     * Verifier.verify('\0').alphanumeric()             =&gt; FAIL
     * Verifier.verify('0').alphanumeric()              =&gt; PASS
     * Verifier.verify('Z').alphanumeric()              =&gt; PASS
     * Verifier.verify('१').alphanumeric()              =&gt; PASS
     * Verifier.verify('É').alphanumeric()              =&gt; PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlphanumeric()
     */
    public CharacterVerifier alphanumeric() {
        final Character value = verification().getValue();
        final boolean result = value != null && Character.isLetterOrDigit(value);

        verification().report(result, MessageKeys.ALPHANUMERIC);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is ASCII.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).ascii() =&gt; FAIL
     * Verifier.verify('\0').ascii()             =&gt; PASS
     * Verifier.verify('0').ascii()              =&gt; PASS
     * Verifier.verify('Z').ascii()              =&gt; PASS
     * Verifier.verify('१').ascii()              =&gt; FAIL
     * Verifier.verify('É').ascii()              =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public CharacterVerifier ascii() {
        final Character value = verification().getValue();
        final boolean result = value != null && value < 128;

        verification().report(result, MessageKeys.ASCII);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an ASCII letter.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiAlpha() =&gt; FAIL
     * Verifier.verify('\0').asciiAlpha()             =&gt; FAIL
     * Verifier.verify('0').asciiAlpha()              =&gt; FAIL
     * Verifier.verify('Z').asciiAlpha()              =&gt; PASS
     * Verifier.verify('१').asciiAlpha()              =&gt; FAIL
     * Verifier.verify('É').asciiAlpha()              =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #alpha()
     */
    public CharacterVerifier asciiAlpha() {
        final Character value = verification().getValue();
        final boolean result = value != null && isAsciiAlpha(value);

        verification().report(result, MessageKeys.ASCII_ALPHA);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an ASCII lower case letter.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiAlphaLowerCase() =&gt; FAIL
     * Verifier.verify('\0').asciiAlphaLowerCase()             =&gt; FAIL
     * Verifier.verify('0').asciiAlphaLowerCase()              =&gt; FAIL
     * Verifier.verify('a').asciiAlphaLowerCase()              =&gt; PASS
     * Verifier.verify('Z').asciiAlphaLowerCase()              =&gt; FAIL
     * Verifier.verify('१').asciiAlphaLowerCase()              =&gt; FAIL
     * Verifier.verify('é').asciiAlphaLowerCase()              =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlphaUpperCase()
     * @see #lowerCase()
     */
    public CharacterVerifier asciiAlphaLowerCase() {
        final Character value = verification().getValue();
        final boolean result = value != null && isAsciiAlphaLowerCase(value);

        verification().report(result, MessageKeys.ASCII_ALPHA_LOWER_CASE);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an ASCII upper case letter.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiAlphaUpperCase() =&gt; FAIL
     * Verifier.verify('\0').asciiAlphaUpperCase()             =&gt; FAIL
     * Verifier.verify('0').asciiAlphaUpperCase()              =&gt; FAIL
     * Verifier.verify('a').asciiAlphaUpperCase()              =&gt; FAIL
     * Verifier.verify('Z').asciiAlphaUpperCase()              =&gt; PASS
     * Verifier.verify('१').asciiAlphaUpperCase()              =&gt; FAIL
     * Verifier.verify('É').asciiAlphaUpperCase()              =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlphaLowerCase()
     * @see #upperCase()
     */
    public CharacterVerifier asciiAlphaUpperCase() {
        final Character value = verification().getValue();
        final boolean result = value != null && isAsciiAlphaUpperCase(value);

        verification().report(result, MessageKeys.ASCII_ALPHA_UPPER_CASE);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an ASCII letter or digit.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiAlphanumeric() =&gt; FAIL
     * Verifier.verify('\0').asciiAlphanumeric()             =&gt; FAIL
     * Verifier.verify('0').asciiAlphanumeric()              =&gt; PASS
     * Verifier.verify('Z').asciiAlphanumeric()              =&gt; PASS
     * Verifier.verify('१').asciiAlphanumeric()              =&gt; FAIL
     * Verifier.verify('É').asciiAlphanumeric()              =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #alphanumeric()
     */
    public CharacterVerifier asciiAlphanumeric() {
        final Character value = verification().getValue();
        final boolean result = value != null && (isAsciiAlpha(value) || isAsciiNumeric(value));

        verification().report(result, MessageKeys.ASCII_ALPHANUMERIC);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an ASCII control.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiControl() =&gt; FAIL
     * Verifier.verify('\0').asciiControl()             =&gt; PASS
     * Verifier.verify('0').asciiControl()              =&gt; FAIL
     * Verifier.verify('Z').asciiControl()              =&gt; FAIL
     * Verifier.verify('१').asciiControl()              =&gt; FAIL
     * Verifier.verify('É').asciiControl()              =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public CharacterVerifier asciiControl() {
        final Character value = verification().getValue();
        final boolean result = value != null && (value < 32 || value == 127);

        verification().report(result, MessageKeys.ASCII_CONTROL);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an ASCII digit.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiNumeric() =&gt; FAIL
     * Verifier.verify('\0').asciiNumeric()             =&gt; FAIL
     * Verifier.verify('0').asciiNumeric()              =&gt; PASS
     * Verifier.verify('Z').asciiNumeric()              =&gt; FAIL
     * Verifier.verify('१').asciiNumeric()              =&gt; FAIL
     * Verifier.verify('É').asciiNumeric()              =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #numeric()
     */
    public CharacterVerifier asciiNumeric() {
        final Character value = verification().getValue();
        final boolean result = value != null && isAsciiNumeric(value);

        verification().report(result, MessageKeys.ASCII_NUMERIC);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is ASCII printable.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).asciiPrintable() =&gt; FAIL
     * Verifier.verify('\0').asciiPrintable()             =&gt; FAIL
     * Verifier.verify('0').asciiPrintable()              =&gt; PASS
     * Verifier.verify('Z').asciiPrintable()              =&gt; PASS
     * Verifier.verify(' ').asciiPrintable()              =&gt; PASS
     * Verifier.verify('~').asciiPrintable()              =&gt; PASS
     * Verifier.verify('१').asciiPrintable()              =&gt; FAIL
     * Verifier.verify('É').asciiPrintable()              =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public CharacterVerifier asciiPrintable() {
        final Character value = verification().getValue();
        final boolean result = value != null && value >= 32 && value < 127;

        verification().report(result, MessageKeys.ASCII_PRINTABLE);

        return this;
    }

    @Override
    public CharacterVerifier falsy() {
        final Character value = verification().getValue();
        final boolean result = value == null || value == '0';

        verification().report(result, BaseTruthVerifier.MessageKeys.FALSY);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a lower case letter.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).lowerCase() =&gt; FAIL
     * Verifier.verify('\0').lowerCase()             =&gt; FAIL
     * Verifier.verify('0').lowerCase()              =&gt; FAIL
     * Verifier.verify('a').lowerCase()              =&gt; PASS
     * Verifier.verify('Z').lowerCase()              =&gt; FAIL
     * Verifier.verify('१').lowerCase()              =&gt; FAIL
     * Verifier.verify('é').lowerCase()              =&gt; PASS
     * Verifier.verify('É').lowerCase()              =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlphaLowerCase()
     * @see #upperCase()
     */
    public CharacterVerifier lowerCase() {
        final Character value = verification().getValue();
        final boolean result = value != null && Character.isLowerCase(value);

        verification().report(result, MessageKeys.LOWER_CASE);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a digit.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).numeric() =&gt; FAIL
     * Verifier.verify('\0').numeric()             =&gt; FAIL
     * Verifier.verify('0').numeric()              =&gt; PASS
     * Verifier.verify('Z').numeric()              =&gt; FAIL
     * Verifier.verify('१').numeric()              =&gt; PASS
     * Verifier.verify('É').numeric()              =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiNumeric()
     */
    public CharacterVerifier numeric() {
        final Character value = verification().getValue();
        final boolean result = value != null && Character.isDigit(value);

        verification().report(result, MessageKeys.NUMERIC);

        return this;
    }

    @Override
    public CharacterVerifier truthy() {
        final Character value = verification().getValue();
        final boolean result = value != null && value == '1';

        verification().report(result, BaseTruthVerifier.MessageKeys.TRUTHY);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an upper case letter.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).upperCase() =&gt; FAIL
     * Verifier.verify('\0').upperCase()             =&gt; FAIL
     * Verifier.verify('0').upperCase()              =&gt; FAIL
     * Verifier.verify('a').upperCase()              =&gt; FAIL
     * Verifier.verify('Z').upperCase()              =&gt; PASS
     * Verifier.verify('१').upperCase()              =&gt; FAIL
     * Verifier.verify('é').upperCase()              =&gt; FAIL
     * Verifier.verify('É').upperCase()              =&gt; PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #asciiAlphaUpperCase()
     * @see #lowerCase()
     */
    public CharacterVerifier upperCase() {
        final Character value = verification().getValue();
        final boolean result = value != null && Character.isUpperCase(value);

        verification().report(result, MessageKeys.UPPER_CASE);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is whitespace.
     * </p>
     * <pre>
     * Verifier.verify((Character) null).whitespace() =&gt; FAIL
     * Verifier.verify('\0').whitespace()             =&gt; FAIL
     * Verifier.verify('0').whitespace()              =&gt; FAIL
     * Verifier.verify('Z').whitespace()              =&gt; FAIL
     * Verifier.verify(' ').whitespace()              =&gt; PASS
     * Verifier.verify('\r').whitespace()             =&gt; PASS
     * Verifier.verify('\n').whitespace()             =&gt; PASS
     * Verifier.verify('\t').whitespace()             =&gt; PASS
     * </pre>
     *
     * @return A reference to this {@link CharacterVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public CharacterVerifier whitespace() {
        final Character value = verification().getValue();
        final boolean result = value != null && Character.isWhitespace(value);

        verification().report(result, MessageKeys.WHITESPACE);

        return this;
    }

    /**
     * <p>
     * The {@link MessageKey MessageKeys} that are used by {@link CharacterVerifier}.
     * </p>
     *
     * @since 0.2.0
     */
    enum MessageKeys implements MessageKey {

        ALPHA("io.skelp.verifier.type.CharacterVerifier.alpha"),
        ALPHANUMERIC("io.skelp.verifier.type.CharacterVerifier.alphanumeric"),
        ASCII("io.skelp.verifier.type.CharacterVerifier.ascii"),
        ASCII_ALPHA("io.skelp.verifier.type.CharacterVerifier.asciiAlpha"),
        ASCII_ALPHA_LOWER_CASE("io.skelp.verifier.type.CharacterVerifier.asciiAlphaLowerCase"),
        ASCII_ALPHA_UPPER_CASE("io.skelp.verifier.type.CharacterVerifier.asciiAlphaUpperCase"),
        ASCII_ALPHANUMERIC("io.skelp.verifier.type.CharacterVerifier.asciiAlphanumeric"),
        ASCII_CONTROL("io.skelp.verifier.type.CharacterVerifier.asciiControl"),
        ASCII_NUMERIC("io.skelp.verifier.type.CharacterVerifier.asciiNumeric"),
        ASCII_PRINTABLE("io.skelp.verifier.type.CharacterVerifier.asciiPrintable"),
        LOWER_CASE("io.skelp.verifier.type.CharacterVerifier.lowerCase"),
        NUMERIC("io.skelp.verifier.type.CharacterVerifier.numeric"),
        UPPER_CASE("io.skelp.verifier.type.CharacterVerifier.upperCase"),
        WHITESPACE("io.skelp.verifier.type.CharacterVerifier.whitespace");

        private final String code;

        MessageKeys(final String code) {
            this.code = code;
        }

        @Override
        public String code() {
            return code;
        }
    }
}
