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
package io.skelp.verifier.type.base;

import java.util.Collection;

import io.skelp.verifier.AbstractCustomVerifier;
import io.skelp.verifier.Verifier;
import io.skelp.verifier.VerifierAssertion;
import io.skelp.verifier.VerifierException;
import io.skelp.verifier.util.Function;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An abstract extension of {@link AbstractCustomVerifier} which includes methods for verifying a value which is a
 * collection of elements. Implementations of {@code BaseCollectionVerifier} must be able to provide a
 * {@code Collection} representation of the value as well as its size.
 * </p>
 *
 * @param <E>
 *         the type of the elements contained within the value being verified
 * @param <T>
 *         the type of the value being verified
 * @param <V>
 *         the type of the {@link BaseCollectionVerifier} for chaining purposes
 * @author Alasdair Mercer
 */
public abstract class BaseCollectionVerifier<E, T, V extends BaseCollectionVerifier<E, T, V>> extends AbstractCustomVerifier<T, V> {

    /**
     * <p>
     * Creates an instance of {@link BaseCollectionVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public BaseCollectionVerifier(final Verification<T> verification) {
        super(verification);
    }

    /**
     * <p>
     * Verifies that the value contains the {@code element} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Object[]) null).contain(*)                 => FAIL
     * Verifier.verify(new Object[0]).contain(*)                   => FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).contain(147)   => FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).contain(789)   => PASS
     * Verifier.verify(new Object[]{123, 456, 789}).contain(null)  => FAIL
     * Verifier.verify(new Object[]{123, 456, null}).contain(null) => PASS
     * </pre>
     *
     * @param element
     *         the object to check for within the value (may be {@literal null})
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V contain(final E element) throws VerifierException {
        final Collection<E> value = getCollection(verification().getValue());
        final boolean result = value != null && value.contains(element);

        verification().check(result, "contain '%s'", element);

        return chain();
    }

    /**
     * <p>
     * Verifies that the value contains <b>all</b> of the {@code elements} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Object[]) null).containAll(*)                      => FAIL
     * Verifier.verify(new Object[0]).containAll(*)                        => FAIL
     * Verifier.verify(new Object[]{*}).containAll((Object[]) null)        => PASS
     * Verifier.verify(new Object[]{123, 456, 789}).containAll(147, 258)   => FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).containAll(123, 147)   => FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).containAll(789, 456)   => PASS
     * Verifier.verify(new Object[]{123, 456, 789}).containAll(789, null)  => FAIL
     * Verifier.verify(new Object[]{123, 456, null}).containAll(123, null) => PASS
     * </pre>
     *
     * @param elements
     *         the objects to check for within the value (may be {@literal null})
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V containAll(final E... elements) throws VerifierException {
        final Collection<E> value = getCollection(verification().getValue());
        final boolean result = value != null && matchAll(elements, new Function<Boolean, E>() {
            @Override
            public Boolean apply(final E input) {
                return value.contains(input);
            }
        });

        verification().check(result, "contain all %s", verification().getMessageFormatter().formatArray(elements));

        return chain();
    }

    /**
     * <p>
     * Verifies that the value contains <b>any</b> of the {@code elements} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Object[]) null).containAny(*)                      => FAIL
     * Verifier.verify(new Object[0]).containAny(*)                        => FAIL
     * Verifier.verify(new Object[]{*}).containAny((Object[]) null)        => FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).containAny(147, 258)   => FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).containAny(123, 147)   => PASS
     * Verifier.verify(new Object[]{123, 456, 789}).containAny(789, 456)   => PASS
     * Verifier.verify(new Object[]{123, 456, 789}).containAny(789, null)  => PASS
     * Verifier.verify(new Object[]{123, 456, null}).containAny(147, null) => PASS
     * </pre>
     *
     * @param elements
     *         the objects to check for within the value (may be {@literal null})
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V containAny(final E... elements) throws VerifierException {
        final Collection<E> value = getCollection(verification().getValue());
        final boolean result = value != null && matchAny(elements, new Function<Boolean, E>() {
            @Override
            public Boolean apply(final E input) {
                return value.contains(input);
            }
        });

        verification().check(result, "contain any %s", verification().getMessageFormatter().formatArray(elements));

        return chain();
    }

    /**
     * <p>
     * Verifies that the value is empty.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Object[]) null).empty()             => PASS
     * Verifier.verify(new Object[0]).empty()               => PASS
     * Verifier.verify(new Object[]{123, 456, 789}).empty() => FAIL
     * </pre>
     *
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V empty() throws VerifierException {
        final T value = verification().getValue();
        final boolean result = value == null || getSize(value) == 0;

        verification().check(result, "be empty");

        return chain();
    }

    /**
     * <p>
     * Verifies that the value is of the {@code size} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Object[]) null).sizeOf(1)             => FAIL
     * Verifier.verify((Object[]) null).sizeOf(0)             => PASS
     * Verifier.verify(new Object[0]).sizeOf(1)               => FAIL
     * Verifier.verify(new Object[0]).sizeOf(0)               => PASS
     * Verifier.verify(new Object[]{123, 456, 789}).sizeOf(0) => FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).sizeOf(3) => PASS
     * </pre>
     *
     * @param size
     *         the size to compare against the number of elements within the value
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V sizeOf(final int size) throws VerifierException {
        final T value = verification().getValue();
        final boolean result = value == null ? size == 0 : getSize(value) == size;

        verification().check(result, "have a size of '%d'", size);

        return chain();
    }

    /**
     * <p>
     * Verifies that <b>all</b> of the elements within the value pass the {@code assertion} provided.
     * </p>
     * <p>
     * Unlike {@link #that(VerifierAssertion)}, this method will pass each of the elements within the value to
     * {@code assertion} instead of the value itself.
     * </p>
     * <pre>
     * Verifier.verify(*).thatAll(null)                                           => FAIL
     * Verifier.verify(*).not().thatAll(null)                                     => FAIL
     * Verifier.verify((Object[]) null).thatAll(value -> true)                    => FAIL
     * Verifier.verify(new Object[0]).thatAll(value -> false)                     => PASS
     * Verifier.verify(new Object[]{123, 456, 789}).thatAll(value -> value < 0)   => FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).thatAll(value -> value > 200) => FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).thatAll(value -> value > 100) => PASS
     * </pre>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the elements within the value
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #thatAll(VerifierAssertion, String, Object...)
     */
    public V thatAll(final VerifierAssertion<E> assertion) throws VerifierException {
        return thatAll(assertion, null);
    }

    /**
     * <p>
     * Verifies that <b>all</b> of the elements within the value pass the {@code assertion} provided while allowing an
     * optional {@code message} and format {@code args} to be specified to enhance the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * Unlike {@link #that(VerifierAssertion, String, Object[])}, this method will pass each of the elements within the
     * value to {@code assertion} instead of the value itself.
     * </p>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the elements within the value
     * @param message
     *         the optional message which provides a (slightly) more detailed explanation of what is being verified
     * @param args
     *         the optional format arguments which are only used to format {@code message}
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #thatAll(VerifierAssertion)
     */
    public V thatAll(final VerifierAssertion<E> assertion, final String message, final Object... args) throws VerifierException {
        Verifier.verify(assertion, "assertion")
            .not().nulled();

        final Collection<E> value = getCollection(verification().getValue());
        final boolean result = matchAll(value, new Function<Boolean, E>() {
            @Override
            public Boolean apply(E input) {
                return assertion.verify(input);
            }
        });

        verification().check(result, message, args);

        return chain();
    }

    /**
     * <p>
     * Verifies that <b>any</b> of the elements within the value pass the {@code assertion} provided.
     * </p>
     * <p>
     * Unlike {@link #that(VerifierAssertion)}, this method will pass each of the elements within the value to
     * {@code assertion} instead of the value itself.
     * </p>
     * <pre>
     * Verifier.verify(*).thatAny(null)                                           => FAIL
     * Verifier.verify(*).not().thatAny(null)                                     => FAIL
     * Verifier.verify((Object[]) null).thatAny(value -> true)                    => FAIL
     * Verifier.verify(new Object[0]).thatAny(value -> true)                      => FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).thatAny(value -> value < 0)   => FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).thatAny(value -> value > 200) => PASS
     * Verifier.verify(new Object[]{123, 456, 789}).thatAny(value -> value > 100) => PASS
     * </pre>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the elements within the value
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #thatAny(VerifierAssertion, String, Object...)
     */
    public V thatAny(final VerifierAssertion<E> assertion) throws VerifierException {
        return thatAny(assertion, null);
    }

    /**
     * <p>
     * Verifies that <b>any</b> of the elements within the value pass the {@code assertion} provided while allowing an
     * optional {@code message} and format {@code args} to be specified to enhance the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * Unlike {@link #that(VerifierAssertion, String, Object[])}, this method will pass each of the elements within the
     * value to {@code assertion} instead of the value itself.
     * </p>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the elements within the value
     * @param message
     *         the optional message which provides a (slightly) more detailed explanation of what is being verified
     * @param args
     *         the optional format arguments which are only used to format {@code message}
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #thatAny(VerifierAssertion)
     */
    public V thatAny(final VerifierAssertion<E> assertion, final String message, final Object... args) throws VerifierException {
        Verifier.verify(assertion, "assertion")
            .not().nulled();

        final Collection<E> value = getCollection(verification().getValue());
        final boolean result = matchAny(value, new Function<Boolean, E>() {
            @Override
            public Boolean apply(E input) {
                return assertion.verify(input);
            }
        });

        verification().check(result, message, args);

        return chain();
    }

    /**
     * <p>
     * Returns a collection representation of the specified {@code value}.
     * </p>
     * <p>
     * Implementations should ensure that this method returns {@literal null} when {@code value} is {@literal null}.
     * </p>
     *
     * @param value
     *         the value to be transformed into a {@code Collection} (may be {@literal null})
     * @return A {@code Collection} instance to represent {@code value} or {@literal null} if {@code value} is {@literal
     * null}.
     */
    protected abstract Collection<E> getCollection(T value);

    /**
     * <p>
     * Returns the size (i.e. number of elements) of the specified {@code value}.
     * </p>
     * <p>
     * This method will <b>never</b> be called when {@code value} is {@literal null} so implementations do not need to
     * be concerned with such a case. This method is simply to avoid unnecessarily transforming {@code value} into a
     * {@code Collection} in order to determine its size as implementations should be able to access this information
     * easier and more efficiently.
     * </p>
     *
     * @param value
     *         the value whose size is to be returned
     * @return The number of elements within {@code value} or zero if {@code value} is empty.
     */
    protected abstract int getSize(T value);
}
