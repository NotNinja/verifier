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
 * <p>
 * An implementation of {@link BaseComparableVerifier} and {@link BaseTruthVerifier} which can be used to verify a
 * {@code String} value.
 * </p>
 * <p>
 * All of the {@link BaseTruthVerifier} methods are implemented so that {@literal null} and {@literal "false"} (ignoring
 * case) are <b>always</b> considered to be falsy and {@literal "true"} (ignoring case) is <b>always</b> considered to
 * be truthy.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class StringVerifier extends BaseComparableVerifier<String, StringVerifier> implements BaseTruthVerifier<String, StringVerifier> {

    private static boolean containsIgnoreCase(final String value, final CharSequence other) {
        if (other == null) {
            return false;
        }

        final int length = other.length();
        final int maximum = value.length() - length;
        for (int i = 0; i <= maximum; i++) {
            if (regionMatches(value, true, i, other, 0, length)) {
                return true;
            }
        }

        return false;
    }

    private static boolean endsWith(final String value, final CharSequence other, final boolean ignoreCase) {
        return other != null && regionMatches(value, ignoreCase, value.length() - other.length(), other, 0, other.length());
    }

    private static boolean isEqualToIgnoreCase(final String value, final CharSequence other) {
        return other == null ? value == null : value != null && regionMatches(value, true, 0, other, 0, value.length());
    }

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

    private static boolean startsWith(final String value, final CharSequence other, final boolean ignoreCase) {
        return other != null && regionMatches(value, ignoreCase, 0, other, 0, other.length());
    }

    /**
     * <p>
     * Creates an instance of {@link StringVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public StringVerifier(final Verification<String> verification) {
        super(verification);
    }

    /**
     * <p>
     * Verifies that the value contains only letters.
     * </p>
     * <pre>
     * Verifier.verify((String) null).alpha() => FAIL
     * Verifier.verify("\0\r\n").alpha()      => FAIL
     * Verifier.verify("123").alpha()         => FAIL
     * Verifier.verify("abc").alpha()         => PASS
     * Verifier.verify("abc123").alpha()      => FAIL
     * Verifier.verify("a b c").alpha()       => FAIL
     * </pre>
     *
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #alphaSpace()
     */
    public StringVerifier alpha() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isLetter(character);
            }
        });

        verification().check(result, "contain only letters");

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains only letters or space.
     * </p>
     * <pre>
     * Verifier.verify((String) null).alphaSpace() => FAIL
     * Verifier.verify("\0 \r \n").alphaSpace()    => FAIL
     * Verifier.verify("1 2 3").alphaSpace()       => FAIL
     * Verifier.verify("a b c").alphaSpace()       => PASS
     * Verifier.verify("a b c 1 2 3").alphaSpace() => FAIL
     * </pre>
     *
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #alpha()
     */
    public StringVerifier alphaSpace() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isLetter(character) || character == ' ';
            }
        });

        verification().check(result, "contain only letters or space");

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains only letters or digits.
     * </p>
     * <pre>
     * Verifier.verify((String) null).alphanumeric() => FAIL
     * Verifier.verify("\0\r\n").alphanumeric()      => FAIL
     * Verifier.verify("123").alphanumeric()         => PASS
     * Verifier.verify("abc").alphanumeric()         => PASS
     * Verifier.verify("abc123").alphanumeric()      => PASS
     * Verifier.verify("a b c 1 2 3").alphanumeric() => FAIL
     * </pre>
     *
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #alphanumericSpace()
     */
    public StringVerifier alphanumeric() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isLetterOrDigit(character);
            }
        });

        verification().check(result, "contain only letters or digits");

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains only letters or digits or space.
     * </p>
     * <pre>
     * Verifier.verify((String) null).alphanumericSpace() => FAIL
     * Verifier.verify("\0 \r \n").alphanumericSpace()    => FAIL
     * Verifier.verify("1 2 3").alphanumericSpace()       => PASS
     * Verifier.verify("a b c").alphanumericSpace()       => PASS
     * Verifier.verify("a b c 1 2 3").alphanumericSpace() => PASS
     * </pre>
     *
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #alphanumeric()
     */
    public StringVerifier alphanumericSpace() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isLetterOrDigit(character) || character == ' ';
            }
        });

        verification().check(result, "contain only letters or digits or space");

        return this;
    }

    /**
     * <p>
     * Verifies that the value only contains ASCII printable characters.
     * </p>
     * <pre>
     * Verifier.verify((String) null).asciiPrintable() => FAIL
     * Verifier.verify("\0 abc").asciiPrintable()      => FAIL
     * Verifier.verify("abc ~ 123").asciiPrintable()   => PASS
     * Verifier.verify("É १").asciiPrintable()         => FAIL
     * </pre>
     *
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public StringVerifier asciiPrintable() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return character >= 32 && character < 127;
            }
        });

        verification().check(result, "contain only ASCII printable characters");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is blank (i.e. only contains whitespace).
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).blank() => PASS
     * Verifier.verify("abc 123").blank()     => FAIL
     * Verifier.verify("  abc 123  ").blank() => FAIL
     * Verifier.verify("  \r\nabc").blank()   => FAIL
     * Verifier.verify("").blank()            => PASS
     * Verifier.verify("  ").blank()          => PASS
     * Verifier.verify("\r\n\t").blank()      => PASS
     * </pre>
     *
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #empty()
     */
    public StringVerifier blank() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value == null || value.trim().isEmpty();

        verification().check(result, "be blank");

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).contain(*)             => FAIL
     * Verifier.verify(*).contain(null)                      => FAIL
     * Verifier.verify(*).contain("")                        => PASS
     * Verifier.verify("abc def 123").contain("ghi")         => FAIL
     * Verifier.verify("abc def 123").contain("def")         => PASS
     * Verifier.verify("abc def 123").contain("abc def 123") => PASS
     * Verifier.verify("abc def 123").contain("DEF")         => FAIL
     * </pre>
     *
     * @param other
     *         the {@code CharSequence} to check for within the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #containIgnoreCase(CharSequence)
     */
    public StringVerifier contain(final CharSequence other) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value != null && other != null && value.contains(other);

        verification().check(result, "contain '%s'", other);

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains <b>all</b> of the {@code others} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).containAll(*)            => FAIL
     * Verifier.verify(*).containAll((CharSequence[]) null)    => PASS
     * Verifier.verify("abc def 123").containAll("ghi", "456") => FAIL
     * Verifier.verify("abc def 123").containAll("def", "456") => FAIL
     * Verifier.verify("abc def 123").containAll("123", "def") => PASS
     * Verifier.verify("abc def 123").containAll("123", "ABC") => FAIL
     * </pre>
     *
     * @param others
     *         the {@code CharSequences} to check for within the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #containAllIgnoreCase(CharSequence...)
     */
    public StringVerifier containAll(final CharSequence... others) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value != null && matchAll(others, new Function<Boolean, CharSequence>() {
            @Override
            public Boolean apply(final CharSequence input) {
                return input != null && value.contains(input);
            }
        });

        verification().check(result, "contain all %s", verification().getMessageFormatter().formatArray(others));

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains <b>all</b> of the {@code others} provided while ignoring case.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).containAllIgnoreCase(*)            => FAIL
     * Verifier.verify(*).containAllIgnoreCase((CharSequence[]) null)    => PASS
     * Verifier.verify("abc def 123").containAllIgnoreCase("ghi", "456") => FAIL
     * Verifier.verify("abc def 123").containAllIgnoreCase("def", "456") => FAIL
     * Verifier.verify("abc def 123").containAllIgnoreCase("123", "def") => PASS
     * Verifier.verify("abc def 123").containAllIgnoreCase("123", "DEF") => PASS
     * </pre>
     *
     * @param others
     *         the {@code CharSequences} to check for within the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #containAll(CharSequence...)
     */
    public StringVerifier containAllIgnoreCase(final CharSequence... others) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value != null && matchAll(others, new Function<Boolean, CharSequence>() {
            @Override
            public Boolean apply(final CharSequence input) {
                return containsIgnoreCase(value, input);
            }
        });

        verification().check(result, "contain all %s (ignore case)", verification().getMessageFormatter().formatArray(others));

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains <b>any</b> of the {@code others} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).containAny(*)            => FAIL
     * Verifier.verify(*).containAny((CharSequence[]) null)    => FAIL
     * Verifier.verify(*).containAny("", *)                    => PASS
     * Verifier.verify("abc def 123").containAny("ghi", "456") => FAIL
     * Verifier.verify("abc def 123").containAny("def", "456") => PASS
     * Verifier.verify("abc def 123").containAny("123", "def") => PASS
     * Verifier.verify("abc def 123").containAny("DEF", "456") => FAIL
     * </pre>
     *
     * @param others
     *         the {@code CharSequences} to check for within the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #containAnyIgnoreCase(CharSequence...)
     */
    public StringVerifier containAny(final CharSequence... others) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value != null && matchAny(others, new Function<Boolean, CharSequence>() {
            @Override
            public Boolean apply(final CharSequence input) {
                return input != null && value.contains(input);
            }
        });

        verification().check(result, "contain any %s", verification().getMessageFormatter().formatArray(others));

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains <b>any</b> of the {@code others} provided while ignoring case.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).containAnyIgnoreCase(*)            => FAIL
     * Verifier.verify(*).containAnyIgnoreCase((CharSequence[]) null)    => FAIL
     * Verifier.verify(*).containAnyIgnoreCase("", *)                    => PASS
     * Verifier.verify("abc def 123").containAnyIgnoreCase("ghi", "456") => FAIL
     * Verifier.verify("abc def 123").containAnyIgnoreCase("def", "456") => PASS
     * Verifier.verify("abc def 123").containAnyIgnoreCase("123", "def") => PASS
     * Verifier.verify("abc def 123").containAnyIgnoreCase("DEF", "456") => PASS
     * </pre>
     *
     * @param others
     *         the {@code CharSequences} to check for within the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #containAny(CharSequence...)
     */
    public StringVerifier containAnyIgnoreCase(final CharSequence... others) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value != null && matchAny(others, new Function<Boolean, CharSequence>() {
            @Override
            public Boolean apply(final CharSequence input) {
                return containsIgnoreCase(value, input);
            }
        });

        verification().check(result, "contain any %s (ignore case)", verification().getMessageFormatter().formatArray(others));

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains the {@code other} provided while ignoring case.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).containIgnoreCase(*)             => FAIL
     * Verifier.verify(*).containIgnoreCase(null)                      => FAIL
     * Verifier.verify(*).containIgnoreCase("")                        => PASS
     * Verifier.verify("abc def 123").containIgnoreCase("ghi")         => FAIL
     * Verifier.verify("abc def 123").containIgnoreCase("def")         => PASS
     * Verifier.verify("abc def 123").containIgnoreCase("abc def 123") => PASS
     * Verifier.verify("abc def 123").containIgnoreCase("DEF")         => PASS
     * </pre>
     *
     * @param other
     *         the {@code CharSequence} to check for within the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #contain(CharSequence)
     */
    public StringVerifier containIgnoreCase(final CharSequence other) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value != null && containsIgnoreCase(value, other);

        verification().check(result, "contain '%s' (ignore case)", other);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is empty.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).empty() => PASS
     * Verifier.verify("abc 123").empty()     => FAIL
     * Verifier.verify("").empty()            => PASS
     * Verifier.verify("  ").empty()          => FAIL
     * Verifier.verify("\r\n\t").empty()      => FAIL
     * </pre>
     *
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #blank()
     */
    public StringVerifier empty() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value == null || value.isEmpty();

        verification().check(result, "be empty");

        return this;
    }

    /**
     * <p>
     * Verifies that the value ends with the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).endWith(*)     => FAIL
     * Verifier.verify(*).endWith(null)              => FAIL
     * Verifier.verify(*).endWith("")                => PASS
     * Verifier.verify("abc def").endWith("abc")     => FAIL
     * Verifier.verify("abc def").endWith("def")     => PASS
     * Verifier.verify("abc def").endWith("abc def") => PASS
     * Verifier.verify("abc def").endWith("DEF")     => FAIL
     * </pre>
     *
     * @param other
     *         the {@code CharSequence} to check for at the end of the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #endWithIgnoreCase(CharSequence)
     */
    public StringVerifier endWith(final CharSequence other) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value != null && endsWith(value, other, false);

        verification().check(result, "end with '%s'", other);

        return this;
    }

    /**
     * <p>
     * Verifies that the value ends with <b>any</b> of the {@code others} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).endWithAny(*)         => FAIL
     * Verifier.verify(*).endWithAny((CharSequence[]) null) => FAIL
     * Verifier.verify(*).endWithAny("", *)                 => PASS
     * Verifier.verify("abc def").endWithAny("ghi", "123")  => FAIL
     * Verifier.verify("abc def").endWithAny("def", "123")  => PASS
     * Verifier.verify("abc def").endWithAny("DEF", "123")  => FAIL
     * </pre>
     *
     * @param others
     *         the {@code CharSequences} to check for at the end of the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #endWithAnyIgnoreCase(CharSequence...)
     */
    public StringVerifier endWithAny(final CharSequence... others) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value != null && matchAny(others, new Function<Boolean, CharSequence>() {
            @Override
            public Boolean apply(final CharSequence input) {
                return endsWith(value, input, false);
            }
        });

        verification().check(result, "end with any %s", verification().getMessageFormatter().formatArray(others));

        return this;
    }

    /**
     * <p>
     * Verifies that the value ends with <b>any</b> of the {@code others} provided while ignoring case.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).endWithAnyIgnoreCase(*)         => FAIL
     * Verifier.verify(*).endWithAnyIgnoreCase((CharSequence[]) null) => FAIL
     * Verifier.verify(*).endWithAnyIgnoreCase("", *)                 => PASS
     * Verifier.verify("abc def").endWithAnyIgnoreCase("ghi", "123")  => FAIL
     * Verifier.verify("abc def").endWithAnyIgnoreCase("def", "123")  => PASS
     * Verifier.verify("abc def").endWithAnyIgnoreCase("DEF", "123")  => PASS
     * </pre>
     *
     * @param others
     *         the {@code CharSequences} to check for at the end of the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #endWithAny(CharSequence...)
     */
    public StringVerifier endWithAnyIgnoreCase(final CharSequence... others) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value != null && matchAny(others, new Function<Boolean, CharSequence>() {
            @Override
            public Boolean apply(final CharSequence input) {
                return endsWith(value, input, true);
            }
        });

        verification().check(result, "end with any %s (ignore case)", verification().getMessageFormatter().formatArray(others));

        return this;
    }

    /**
     * <p>
     * Verifies that the value ends with the {@code other} provided while ignoring case.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).endWithIgnoreCase(*)     => FAIL
     * Verifier.verify(*).endWithIgnoreCase(null)              => FAIL
     * Verifier.verify(*).endWithIgnoreCase("")                => PASS
     * Verifier.verify("abc def").endWithIgnoreCase("abc")     => FAIL
     * Verifier.verify("abc def").endWithIgnoreCase("def")     => PASS
     * Verifier.verify("abc def").endWithIgnoreCase("abc def") => PASS
     * Verifier.verify("abc def").endWithIgnoreCase("DEF")     => PASS
     * </pre>
     *
     * @param other
     *         the {@code CharSequence} to check for at the end of the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #endWith(CharSequence)
     */
    public StringVerifier endWithIgnoreCase(final CharSequence other) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value != null && endsWith(value, other, true);

        verification().check(result, "end with '%s' (ignore case)", other);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is equal to any of the {@code others} provided while ignoring case.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).equalToAnyIgnoreCase()                                => FAIL
     * Verifier.verify(*).equalToAnyIgnoreCase((CharSequence[]) null)           => FAIL
     * Verifier.verify((String) null).equalToAnyIgnoreCase("ghi", "def", null)  => PASS
     * Verifier.verify((String) null).equalToAnyIgnoreCase("ghi", "def", "abc") => FAIL
     * Verifier.verify("abc").equalToAnyIgnoreCase("ghi", "def", null)          => FAIL
     * Verifier.verify("abc").equalToAnyIgnoreCase("ghi", "def", "abc")         => PASS
     * Verifier.verify("abc").equalToAnyIgnoreCase("GHI", "DEF", "ABC")         => PASS
     * </pre>
     *
     * @param others
     *         the {@code CharSequences} to compare against the value (may be {@literal null} or contain {@literal null}
     *         references)
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #equalToAny(Object...)
     */
    public StringVerifier equalToAnyIgnoreCase(final CharSequence... others) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = matchAny(others, new Function<Boolean, CharSequence>() {
            @Override
            public Boolean apply(final CharSequence input) {
                return isEqualToIgnoreCase(value, input);
            }
        });

        verification().check(result, "be equal to any %s (ignore case)", verification().getMessageFormatter().formatArray(others));

        return chain();
    }

    /**
     * <p>
     * Verifies that the value is equal to the {@code other} provided while ignoring case.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).equalToIgnoreCase(null)  => PASS
     * Verifier.verify((String) null).equalToIgnoreCase("abc") => FAIL
     * Verifier.verify("abc").equalToIgnoreCase(null)          => FAIL
     * Verifier.verify("abc").equalToIgnoreCase("abc")         => PASS
     * Verifier.verify("abc").equalToIgnoreCase("ABC")         => PASS
     * Verifier.verify("abc").equalToIgnoreCase("def")         => FAIL
     * </pre>
     *
     * @param other
     *         the {@code CharSequence} to compare against the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #equalTo(Object)
     */
    public StringVerifier equalToIgnoreCase(final CharSequence other) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = isEqualToIgnoreCase(value, other);

        verification().check(result, "be equal to '%s' (ignore case)", other);

        return this;
    }

    @Override
    public StringVerifier falsy() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value == null || value.isEmpty() || Boolean.FALSE.toString().equalsIgnoreCase(value);

        verification().check(result, FALSY_MESSAGE);

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains only lower case letters.
     * </p>
     * <pre>
     * Verifier.verify((String) null).lowerCase() => FAIL
     * Verifier.verify("ABC").lowerCase()         => FAIL
     * Verifier.verify("abcDEF").lowerCase()      => FAIL
     * Verifier.verify("abc123").lowerCase()      => FAIL
     * Verifier.verify("abc").lowerCase()         => PASS
     * Verifier.verify("a b c").lowerCase()       => FAIL
     * </pre>
     *
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #upperCase()
     */
    public StringVerifier lowerCase() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isLowerCase(character);
            }
        });

        verification().check(result, "be all lower case");

        return this;
    }

    /**
     * <p>
     * Verifies that the value matches the regular expression provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).match(*)       => FAIL
     * Verifier.verify(*).match((CharSequence) null) => FAIL
     * Verifier.verify(*).match(".*")                => PASS
     * Verifier.verify("foo").match("fo{2}")         => PASS
     * Verifier.verify("food").match("fo{2}")        => FAIL
     * </pre>
     *
     * @param regex
     *         the regular expression to be matched against the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #match(Pattern)
     */
    public StringVerifier match(final CharSequence regex) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value != null && regex != null && value.matches(regex.toString());

        verification().check(result, "match '%s'", regex);

        return this;
    }

    /**
     * <p>
     * Verifies that the value matches the regular expression {@code pattern} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).match(*)                 => FAIL
     * Verifier.verify(*).match((Pattern) null)                => FAIL
     * Verifier.verify(*).match(Pattern.compile(".*"))         => PASS
     * Verifier.verify("foo").match(Pattern.compile("fo{2}"))  => PASS
     * Verifier.verify("food").match(Pattern.compile("fo{2}")) => FAIL
     * </pre>
     *
     * @param pattern
     *         the regular expression {@code Pattern} to be matched against the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #match(CharSequence)
     */
    public StringVerifier match(final Pattern pattern) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value != null && pattern != null && pattern.matcher(value).matches();

        verification().check(result, "match '%s'", pattern);

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains only digits.
     * </p>
     * <pre>
     * Verifier.verify((String) null).numeric() => FAIL
     * Verifier.verify("\0\r\n").numeric()      => FAIL
     * Verifier.verify("123").numeric()         => PASS
     * Verifier.verify("abc").numeric()         => FAIL
     * Verifier.verify("abc123").numeric()      => FAIL
     * Verifier.verify("1 2 3").numeric()       => FAIL
     * </pre>
     *
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #numericSpace()
     */
    public StringVerifier numeric() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isDigit(character);
            }
        });

        verification().check(result, "contain only digits");

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains only digits or space.
     * </p>
     * <pre>
     * Verifier.verify((String) null).numericSpace() => FAIL
     * Verifier.verify("\0 \r \n").numericSpace()    => FAIL
     * Verifier.verify("1 2 3").numericSpace()       => PASS
     * Verifier.verify("a b c").numericSpace()       => FAIL
     * Verifier.verify("a b c 1 2 3").numericSpace() => FAIL
     * </pre>
     *
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #numeric()
     */
    public StringVerifier numericSpace() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isDigit(character) || character == ' ';
            }
        });

        verification().check(result, "contain only digits or space");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is of the {@code size} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).sizeOf(1) => FAIL
     * Verifier.verify((String) null).sizeOf(0) => PASS
     * Verifier.verify("").sizeOf(1)            => FAIL
     * Verifier.verify("").sizeOf(0)            => PASS
     * Verifier.verify(" ").sizeOf(1)           => PASS
     * Verifier.verify("abc 123").sizeOf(0)     => FAIL
     * Verifier.verify("abc 123").sizeOf(7)     => PASS
     * </pre>
     *
     * @param size
     *         the size to compare against the number of characters within the value
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public StringVerifier sizeOf(final int size) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = value == null ? size == 0 : value.length() == size;

        verification().check(result, "have a size of '%d'", size);

        return this;
    }

    /**
     * <p>
     * Verifies that the value starts with the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).startWith(*)     => FAIL
     * Verifier.verify(*).startWith(null)              => FAIL
     * Verifier.verify(*).startWith("")                => PASS
     * Verifier.verify("abc def").startWith("abc")     => PASS
     * Verifier.verify("abc def").startWith("def")     => FAIL
     * Verifier.verify("abc def").startWith("abc def") => PASS
     * Verifier.verify("abc def").startWith("ABC")     => FAIL
     * </pre>
     *
     * @param other
     *         the {@code CharSequence} to check for at the start of the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #startWithIgnoreCase(CharSequence)
     */
    public StringVerifier startWith(final CharSequence other) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = startsWith(value, other, false);

        verification().check(result, "start with '%s'", other);

        return this;
    }

    /**
     * <p>
     * Verifies that the value starts with <b>any</b> of the {@code others} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).startWithAny(*)         => FAIL
     * Verifier.verify(*).startWithAny((CharSequence[]) null) => FAIL
     * Verifier.verify(*).startWithAny("", *)                 => PASS
     * Verifier.verify("abc def").startWithAny("ghi", "123")  => FAIL
     * Verifier.verify("abc def").startWithAny("abc", "123")  => PASS
     * Verifier.verify("abc def").startWithAny("ABC", "123")  => FAIL
     * </pre>
     *
     * @param others
     *         the {@code CharSequences} to check for at the start of the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #startWithAnyIgnoreCase(CharSequence...)
     */
    public StringVerifier startWithAny(final CharSequence... others) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = matchAny(others, new Function<Boolean, CharSequence>() {
            @Override
            public Boolean apply(final CharSequence input) {
                return startsWith(value, input, false);
            }
        });

        verification().check(result, "start with any %s", verification().getMessageFormatter().formatArray(others));

        return this;
    }

    /**
     * <p>
     * Verifies that the value starts with <b>any</b> of the {@code others} provided while ignoring case.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).startWithAnyIgnoreCase(*)         => FAIL
     * Verifier.verify(*).startWithAnyIgnoreCase((CharSequence[]) null) => FAIL
     * Verifier.verify(*).startWithAnyIgnoreCase("", *)                 => PASS
     * Verifier.verify("abc def").startWithAnyIgnoreCase("ghi", "123")  => FAIL
     * Verifier.verify("abc def").startWithAnyIgnoreCase("abc", "123")  => PASS
     * Verifier.verify("abc def").startWithAnyIgnoreCase("ABC", "123")  => PASS
     * </pre>
     *
     * @param others
     *         the {@code CharSequences} to check for at the start of the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #startWithAny(CharSequence...)
     */
    public StringVerifier startWithAnyIgnoreCase(final CharSequence... others) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = matchAny(others, new Function<Boolean, CharSequence>() {
            @Override
            public Boolean apply(final CharSequence input) {
                return startsWith(value, input, true);
            }
        });

        verification().check(result, "start with any %s (ignore case)", verification().getMessageFormatter().formatArray(others));

        return this;
    }

    /**
     * <p>
     * Verifies that the value starts with the {@code other} provided while ignoring case.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((String) null).startWithIgnoreCase(*)     => FAIL
     * Verifier.verify(*).startWithIgnoreCase(null)              => FAIL
     * Verifier.verify(*).startWithIgnoreCase("")                => PASS
     * Verifier.verify("abc def").startWithIgnoreCase("abc")     => PASS
     * Verifier.verify("abc def").startWithIgnoreCase("def")     => FAIL
     * Verifier.verify("abc def").startWithIgnoreCase("abc def") => PASS
     * Verifier.verify("abc def").startWithIgnoreCase("ABC")     => PASS
     * </pre>
     *
     * @param other
     *         the {@code CharSequence} to check for at the start of the value (may be {@literal null})
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #startWith(CharSequence)
     */
    public StringVerifier startWithIgnoreCase(final CharSequence other) throws VerifierException {
        final String value = verification().getValue();
        final boolean result = startsWith(value, other, true);

        verification().check(result, "start with '%s' (ignore case)", other);

        return this;
    }

    @Override
    public StringVerifier truthy() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = Boolean.TRUE.toString().equalsIgnoreCase(value);

        verification().check(result, TRUTHY_MESSAGE);

        return this;
    }

    /**
     * <p>
     * Verifies that the value contains only upper case letters.
     * </p>
     * <pre>
     * Verifier.verify((String) null).upperCase() => FAIL
     * Verifier.verify("abc").upperCase()         => FAIL
     * Verifier.verify("abcDEF").upperCase()      => FAIL
     * Verifier.verify("ABC123").upperCase()      => FAIL
     * Verifier.verify("ABC").upperCase()         => PASS
     * Verifier.verify("A B C").upperCase()       => FAIL
     * </pre>
     *
     * @return A reference to this {@link StringVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #lowerCase()
     */
    public StringVerifier upperCase() throws VerifierException {
        final String value = verification().getValue();
        final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
            @Override
            public Boolean apply(final Character character) {
                return Character.isUpperCase(character);
            }
        });

        verification().check(result, "be all upper case");

        return this;
    }
}
