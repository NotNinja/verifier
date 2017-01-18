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
package io.skelp.verifier;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An abstract implementation of {@link CustomVerifier} that fulfils the contract while also providing useful helper
 * methods for its children to meet similar verification methods and other abstract implementations.
 * </p>
 *
 * @param <T>
 *         the type of the value being verified
 * @param <V>
 *         the type of the {@link AbstractCustomVerifier} for chaining purposes
 * @author Alasdair Mercer
 */
public abstract class AbstractCustomVerifier<T, V extends AbstractCustomVerifier<T, V>> implements CustomVerifier<T, V> {

    /**
     * <p>
     * Returns whether the specified {@code matcher} matches <b>all</b> of the {@code inputs} provided.
     * </p>
     * <p>
     * This method will return {@literal true} if {@code inputs} is {@literal null} and will return {@literal false} if
     * {@code matcher} returns {@literal false} for any {@code input}.
     * </p>
     *
     * @param inputs
     *         the input values to be matched
     * @param matcher
     *         the {@code Function} to be used to match each input value
     * @param <I>
     *         the type of the input values
     * @return {@literal true} if {@code inputs} is {@literal null} or {@code matcher} returns {@literal true} for all
     * input values; otherwise {@literal false}.
     * @see #matchAll(Collection, Function)
     */
    protected static <I> boolean matchAll(final I[] inputs, final Function<I, Boolean> matcher) {
        return inputs == null || matchAll(Arrays.asList(inputs), matcher);
    }

    /**
     * <p>
     * Returns whether the specified {@code matcher} matches <b>all</b> of the {@code inputs} provided.
     * </p>
     * <p>
     * This method will return {@literal true} if {@code inputs} is {@literal null} and will return {@literal false} if
     * {@code matcher} returns {@literal false} for any {@code input}.
     * </p>
     *
     * @param inputs
     *         the input values to be matched
     * @param matcher
     *         the {@code Function} to be used to match each input value
     * @param <I>
     *         the type of the input values
     * @return {@literal true} if {@code inputs} is {@literal null} or {@code matcher} returns {@literal true} for all
     * input values; otherwise {@literal false}.
     * @see #matchAll(Object[], Function)
     */
    protected static <I> boolean matchAll(final Collection<I> inputs, final Function<I, Boolean> matcher) {
        if (inputs == null) {
            return true;
        }

        for (final I input : inputs) {
            if (!matcher.apply(input)) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>
     * Returns whether the specified {@code matcher} matches <b>any</b> of the {@code inputs} provided.
     * </p>
     * <p>
     * This method will return {@literal false} if {@code inputs} is {@literal null} and will return {@literal true} if
     * {@code matcher} returns {@literal true} for any {@code input}.
     * </p>
     *
     * @param inputs
     *         the input values to be matched
     * @param matcher
     *         the {@code Function} to be used to match each input value
     * @param <I>
     *         the type of the input values
     * @return {@literal true} if {@code inputs} is not {@literal null} and {@code matcher} returns {@literal true} for
     * any input value; otherwise {@literal false}.
     * @see #matchAny(Collection, Function)
     */
    protected static <I> boolean matchAny(final I[] inputs, final Function<I, Boolean> matcher) {
        return inputs != null && matchAny(Arrays.asList(inputs), matcher);
    }

    /**
     * <p>
     * Returns whether the specified {@code matcher} matches <b>any</b> of the {@code inputs} provided.
     * </p>
     * <p>
     * This method will return {@literal false} if {@code inputs} is {@literal null} and will return {@literal true} if
     * {@code matcher} returns {@literal true} for any {@code input}.
     * </p>
     *
     * @param inputs
     *         the input values to be matched
     * @param matcher
     *         the {@code Function} to be used to match each input value
     * @param <I>
     *         the type of the input values
     * @return {@literal true} if {@code inputs} is not {@literal null} and {@code matcher} returns {@literal true} for
     * any input value; otherwise {@literal false}.
     * @see #matchAny(Object[], Function)
     */
    protected static <I> boolean matchAny(final Collection<I> inputs, final Function<I, Boolean> matcher) {
        if (inputs == null) {
            return false;
        }

        for (final I input : inputs) {
            if (matcher.apply(input)) {
                return true;
            }
        }

        return false;
    }

    private final Verification<T> verification;

    /**
     * <p>
     * Creates an instance of {@link AbstractCustomVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public AbstractCustomVerifier(final Verification<T> verification) {
        this.verification = verification;
    }

    /**
     * <p>
     * Returns a reference to this {@link AbstractCustomVerifier} implementation which can be useful for chaining
     * references while avoiding unchecked casting warnings.
     * </p>
     *
     * @return A reference to this {@link AbstractCustomVerifier} implementation.
     */
    protected V chain() {
        @SuppressWarnings("unchecked")
        final V chain = (V) this;
        return chain;
    }

    @Override
    public V equalTo(final Object other) throws VerifierException {
        return equalTo(other, other);
    }

    @Override
    public V equalTo(final Object other, final Object name) throws VerifierException {
        final T value = verification.getValue();
        final boolean result = isEqualTo(value, other);

        verification.check(result, "be equal to '%s'", name);

        return chain();
    }

    @Override
    public V equalToAny(final Object... others) throws VerifierException {
        final T value = verification.getValue();
        final boolean result = matchAny(others, input -> isEqualTo(value, input));

        verification.check(result, "be equal to any %s", verification.getMessageFormatter().formatArray(others));

        return chain();
    }

    @Override
    public V hashedAs(final int hashCode) throws VerifierException {
        final T value = verification.getValue();
        final boolean result = value != null && value.hashCode() == hashCode;

        verification.check(result, "have hash code '%d'", hashCode);

        return chain();
    }

    @Override
    public V instanceOf(final Class<?> cls) throws VerifierException {
        final boolean result = cls != null && cls.isInstance(verification.getValue());

        verification.check(result, "be an instance of '%s'", cls);

        return chain();
    }

    @Override
    public V instanceOfAll(final Class<?>... classes) throws VerifierException {
        final T value = verification.getValue();
        final boolean result = value != null && matchAll(classes, input -> input != null && input.isInstance(value));

        verification.check(result, "be an instance of all %s", verification.getMessageFormatter().formatArray(classes));

        return chain();
    }

    @Override
    public V instanceOfAny(final Class<?>... classes) throws VerifierException {
        final T value = verification.getValue();
        final boolean result = value != null && matchAny(classes, input -> input != null && input.isInstance(value));

        verification.check(result, "be an instance of any %s", verification.getMessageFormatter().formatArray(classes));

        return chain();
    }

    /**
     * <p>
     * Returns whether the specified {@code value} is equal to the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     *
     * @param value
     *         the value being verified (may be {@literal null})
     * @param other
     *         the object to compare against {@code value} (may be {@literal null})
     * @return {@literal true} if {@code value} equals {@code other}; otherwise {@literal false}.
     * @see Object#equals(Object)
     */
    protected boolean isEqualTo(final T value, final Object other) {
        return other == null ? value == null : other.equals(value);
    }

    @Override
    public V not() {
        verification.setNegated(!verification.isNegated());

        return chain();
    }

    @Override
    public V nulled() throws VerifierException {
        final boolean result = verification.getValue() == null;

        verification.check(result, "be null");

        return chain();
    }

    @Override
    public V sameAs(final Object other) throws VerifierException {
        return sameAs(other, other);
    }

    @Override
    public V sameAs(final Object other, final Object name) throws VerifierException {
        final boolean result = verification.getValue() == other;

        verification.check(result, "be same as '%s'", name);

        return chain();
    }

    @Override
    public V sameAsAny(final Object... others) throws VerifierException {
        final T value = verification.getValue();
        final boolean result = matchAny(others, input -> value == input);

        verification.check(result, "be same as any %s", verification.getMessageFormatter().formatArray(others));

        return chain();
    }

    @Override
    public V that(final VerifierAssertion<T> assertion) throws VerifierException {
        return that(assertion, null);
    }

    @Override
    public V that(final VerifierAssertion<T> assertion, final String message, final Object... args) throws VerifierException {
        Verifier.verify(assertion, "assertion")
            .not().nulled();

        final boolean result = assertion.verify(verification.getValue());

        verification.check(result, message, args);

        return chain();
    }

    @Override
    public T value() {
        return verification.getValue();
    }

    @Override
    public Verification<T> verification() {
        return verification;
    }
}
