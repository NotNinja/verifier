/*
 * Copyright (C) 2017 Alasdair Mercer, !ninja
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
package org.notninja.verifier.type.base;

import java.util.Collection;

import org.notninja.verifier.AbstractCustomVerifier;
import org.notninja.verifier.Verifier;
import org.notninja.verifier.VerifierAssertion;
import org.notninja.verifier.VerifierException;
import org.notninja.verifier.message.MessageKey;
import org.notninja.verifier.verification.Verification;

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
     * Verifier.verify((Object[]) null).contain(*)                 =&gt; FAIL
     * Verifier.verify(new Object[0]).contain(*)                   =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).contain(147)   =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).contain(789)   =&gt; PASS
     * Verifier.verify(new Object[]{123, 456, 789}).contain(null)  =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, null}).contain(null) =&gt; PASS
     * </pre>
     *
     * @param element
     *         the object to check for within the value (may be {@literal null})
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V contain(final E element) {
        final Collection<E> value = getCollection(verification().getValue());
        final boolean result = value != null && value.contains(element);

        verification().report(result, MessageKeys.CONTAIN, element);

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
     * Verifier.verify((Object[]) null).containAll(*)                      =&gt; FAIL
     * Verifier.verify(new Object[0]).containAll(*)                        =&gt; FAIL
     * Verifier.verify(new Object[]{*}).containAll((Object[]) null)        =&gt; PASS
     * Verifier.verify(new Object[]{123, 456, 789}).containAll(147, 258)   =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).containAll(123, 147)   =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).containAll(789, 456)   =&gt; PASS
     * Verifier.verify(new Object[]{123, 456, 789}).containAll(789, null)  =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, null}).containAll(123, null) =&gt; PASS
     * </pre>
     *
     * @param elements
     *         the objects to check for within the value (may be {@literal null})
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V containAll(final E... elements) {
        final Collection<E> value = getCollection(verification().getValue());
        final boolean result = value != null && matchAll(elements, value::contains);

        verification().report(result, MessageKeys.CONTAIN_ALL, (Object) elements);

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
     * Verifier.verify((Object[]) null).containAny(*)                      =&gt; FAIL
     * Verifier.verify(new Object[0]).containAny(*)                        =&gt; FAIL
     * Verifier.verify(new Object[]{*}).containAny((Object[]) null)        =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).containAny(147, 258)   =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).containAny(123, 147)   =&gt; PASS
     * Verifier.verify(new Object[]{123, 456, 789}).containAny(789, 456)   =&gt; PASS
     * Verifier.verify(new Object[]{123, 456, 789}).containAny(789, null)  =&gt; PASS
     * Verifier.verify(new Object[]{123, 456, null}).containAny(147, null) =&gt; PASS
     * </pre>
     *
     * @param elements
     *         the objects to check for within the value (may be {@literal null})
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V containAny(final E... elements) {
        final Collection<E> value = getCollection(verification().getValue());
        final boolean result = value != null && matchAny(elements, value::contains);

        verification().report(result, MessageKeys.CONTAIN_ANY, (Object) elements);

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
     * Verifier.verify((Object[]) null).empty()             =&gt; PASS
     * Verifier.verify(new Object[0]).empty()               =&gt; PASS
     * Verifier.verify(new Object[]{123, 456, 789}).empty() =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V empty() {
        final T value = verification().getValue();
        final boolean result = value == null || getSize(value) == 0;

        verification().report(result, MessageKeys.EMPTY);

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
     * Verifier.verify((Object[]) null).sizeOf(1)             =&gt; FAIL
     * Verifier.verify((Object[]) null).sizeOf(0)             =&gt; PASS
     * Verifier.verify(new Object[0]).sizeOf(1)               =&gt; FAIL
     * Verifier.verify(new Object[0]).sizeOf(0)               =&gt; PASS
     * Verifier.verify(new Object[]{123, 456, 789}).sizeOf(0) =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).sizeOf(3) =&gt; PASS
     * </pre>
     *
     * @param size
     *         the size to compare against the number of elements within the value
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public V sizeOf(final int size) {
        final T value = verification().getValue();
        final boolean result = value == null ? size == 0 : getSize(value) == size;

        verification().report(result, MessageKeys.SIZE_OF, size);

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
     * Verifier.verify(*).thatAll(null)                                                 =&gt; FAIL
     * Verifier.verify(*).not().thatAll(null)                                           =&gt; FAIL
     * Verifier.verify((Object[]) null).thatAll(value -&gt; true)                       =&gt; FAIL
     * Verifier.verify(new Object[0]).thatAll(value -&gt; false)                        =&gt; PASS
     * Verifier.verify(new Object[]{123, 456, 789}).thatAll(value -&gt; value &lt; 0)   =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).thatAll(value -&gt; value &gt; 200) =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).thatAll(value -&gt; value &gt; 100) =&gt; PASS
     * </pre>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the elements within the value
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #thatAll(VerifierAssertion, MessageKey, Object...)
     * @see #thatAll(VerifierAssertion, String, Object...)
     */
    public V thatAll(final VerifierAssertion<E> assertion) {
        verification().report(thatAllInternal(assertion), (String) null);

        return chain();
    }

    /**
     * <p>
     * Verifies that <b>all</b> of the elements within the value pass the {@code assertion} provided while allowing an
     * optional {@code key} and format {@code args} to be specified to enhance the {@link VerifierException} message in
     * the event that one is thrown.
     * </p>
     * <p>
     * Unlike {@link #that(VerifierAssertion, MessageKey, Object[])}, this method will pass each of the elements within
     * the value to {@code assertion} instead of the value itself.
     * </p>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the elements within the value
     * @param key
     *         the optional {@link MessageKey} which provides a more detailed localized explanation of what is being
     *         verified
     * @param args
     *         the optional format arguments which are only used to format the localized message
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #thatAll(VerifierAssertion)
     * @see #thatAll(VerifierAssertion, String, Object...)
     * @since 0.2.0
     */
    public V thatAll(final VerifierAssertion<E> assertion, final MessageKey key, final Object... args) {
        verification().report(thatAllInternal(assertion), key, args);

        return chain();
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
     *         the optional message which provides a more detailed explanation of what is being verified
     * @param args
     *         the optional format arguments which are only used to format {@code message}
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #thatAll(VerifierAssertion)
     * @see #thatAll(VerifierAssertion, MessageKey, Object...)
     */
    public V thatAll(final VerifierAssertion<E> assertion, final String message, final Object... args) {
        verification().report(thatAllInternal(assertion), message, args);

        return chain();
    }

    /**
     * <p>
     * Returns whether <b>all</b> of the elements within the value pass the {@code assertion} provided.
     * </p>
     * <p>
     * {@code assertion} will be passed all elements within the value individually.
     * </p>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the elements within the value
     * @return {@literal true} if the {@code assertion} successfully verifies all elements within the value; otherwise
     * {@literal false}.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null}.
     * @see #thatAll(VerifierAssertion)
     * @see #thatAll(VerifierAssertion, MessageKey, Object...)
     * @see #thatAll(VerifierAssertion, String, Object...)
     * @since 0.2.0
     */
    protected boolean thatAllInternal(final VerifierAssertion<E> assertion) {
        Verifier.verify(assertion, "assertion")
            .not().nulled();

        final Collection<E> value = getCollection(verification().getValue());

        return matchAll(value, assertion::verify);
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
     * Verifier.verify(*).thatAny(null)                                                 =&gt; FAIL
     * Verifier.verify(*).not().thatAny(null)                                           =&gt; FAIL
     * Verifier.verify((Object[]) null).thatAny(value -&gt; true)                       =&gt; FAIL
     * Verifier.verify(new Object[0]).thatAny(value -&gt; true)                         =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).thatAny(value -&gt; value &lt; 0)   =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).thatAny(value -&gt; value &gt; 200) =&gt; PASS
     * Verifier.verify(new Object[]{123, 456, 789}).thatAny(value -&gt; value &gt; 100) =&gt; PASS
     * </pre>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the elements within the value
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #thatAny(VerifierAssertion, MessageKey, Object...)
     * @see #thatAny(VerifierAssertion, String, Object...)
     */
    public V thatAny(final VerifierAssertion<E> assertion) {
        verification().report(thatAnyInternal(assertion), (String) null);

        return chain();
    }

    /**
     * <p>
     * Verifies that <b>any</b> of the elements within the value pass the {@code assertion} provided while allowing an
     * optional {@code key} and format {@code args} to be specified to enhance the {@link VerifierException} message in
     * the event that one is thrown.
     * </p>
     * <p>
     * Unlike {@link #that(VerifierAssertion, MessageKey, Object[])}, this method will pass each of the elements within
     * the value to {@code assertion} instead of the value itself.
     * </p>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the elements within the value
     * @param key
     *         the optional {@link MessageKey} which provides a more detailed localized explanation of what is being
     *         verified
     * @param args
     *         the optional format arguments which are only used to format the localized message
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #thatAny(VerifierAssertion)
     * @see #thatAny(VerifierAssertion, String, Object...)
     * @since 0.2.0
     */
    public V thatAny(final VerifierAssertion<E> assertion, final MessageKey key, final Object... args) {
        verification().report(thatAnyInternal(assertion), key, args);

        return chain();
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
     *         the optional message which provides a more detailed explanation of what is being verified
     * @param args
     *         the optional format arguments which are only used to format {@code message}
     * @return A reference to this {@link BaseCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #thatAny(VerifierAssertion)
     * @see #thatAny(VerifierAssertion, MessageKey, Object...)
     */
    public V thatAny(final VerifierAssertion<E> assertion, final String message, final Object... args) {
        verification().report(thatAnyInternal(assertion), message, args);

        return chain();
    }

    /**
     * <p>
     * Returns whether <b>any</b> of the elements within the value pass the {@code assertion} provided.
     * </p>
     * <p>
     * {@code assertion} will be passed all elements within the value individually until one passes.
     * </p>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the elements within the value
     * @return {@literal true} if the {@code assertion} successfully verifies any element within the value; otherwise
     * {@literal false}.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null}.
     * @see #thatAny(VerifierAssertion)
     * @see #thatAny(VerifierAssertion, MessageKey, Object...)
     * @see #thatAny(VerifierAssertion, String, Object...)
     * @since 0.2.0
     */
    protected boolean thatAnyInternal(final VerifierAssertion<E> assertion) {
        Verifier.verify(assertion, "assertion")
            .not().nulled();

        final Collection<E> value = getCollection(verification().getValue());

        return matchAny(value, assertion::verify);
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

    /**
     * <p>
     * The {@link MessageKey MessageKeys} that are used by {@link BaseCollectionVerifier}.
     * </p>
     *
     * @since 0.2.0
     */
    public enum MessageKeys implements MessageKey {

        CONTAIN("org.notninja.verifier.type.base.BaseCollectionVerifier.contain"),
        CONTAIN_ALL("org.notninja.verifier.type.base.BaseCollectionVerifier.containAll"),
        CONTAIN_ANY("org.notninja.verifier.type.base.BaseCollectionVerifier.containAny"),
        EMPTY("org.notninja.verifier.type.base.BaseCollectionVerifier.empty"),
        SIZE_OF("org.notninja.verifier.type.base.BaseCollectionVerifier.sizeOf");

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
