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
import java.util.Comparator;
import java.util.Iterator;

import io.skelp.verifier.Verifier;
import io.skelp.verifier.VerifierException;
import io.skelp.verifier.message.MessageKey;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An abstract extension of {@link BaseSortableCollectionVerifier} which includes methods for verifying how the
 * collection of elements within the value are sorted.
 * </p>
 *
 * @param <E>
 *         the type of the elements contained within the value being verified
 * @param <T>
 *         the type of the value being verified
 * @param <V>
 *         the type of the {@link BaseSortableCollectionVerifier} for chaining purposes
 * @author Alasdair Mercer
 */
public abstract class BaseSortableCollectionVerifier<E, T, V extends BaseSortableCollectionVerifier<E, T, V>> extends BaseCollectionVerifier<E, T, V> {

    /**
     * <p>
     * Creates an instance of {@link BaseSortableCollectionVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public BaseSortableCollectionVerifier(final Verification<T> verification) {
        super(verification);
    }

    /**
     * <p>
     * Verifies that the elements contained within the value are sorted by the {@code comparator} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sortedBy(null)                                                      =&gt; FAIL
     * Verifier.verify(*).not().sortedBy(null)                                                =&gt; FAIL
     * Verifier.verify((Object[]) null).sortedBy(*)                                           =&gt; FAIL
     * Verifier.verify(new Object[0]).sortedBy(*)                                             =&gt; PASS
     * Verifier.verify(new Object[]{123}).sortedBy(*)                                         =&gt; PASS
     * Verifier.verify(new Object[]{987, 654, 321}).sortedBy((o1, o2) -&gt; o1.compareTo(o2)) =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).sortedBy((o1, o2) -&gt; o1.compareTo(o2)) =&gt; PASS
     * </pre>
     *
     * @param comparator
     *         the {@code Comparator} to be used to verify the sort order of the elements within the value
     * @return A reference to this {@link BaseSortableCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code comparator} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #sortedBy(Comparator, Object)
     */
    public V sortedBy(final Comparator<E> comparator) {
        return sortedBy(comparator, comparator);
    }

    /**
     * <p>
     * Verifies that the elements contained within the value are sorted by the {@code comparator} provided while
     * allowing {@code comparator} to be given an optional friendlier {@code name} for the {@link VerifierException}
     * message in the event that one is thrown.
     * </p>
     * <p>
     * While {@code name} can be {@literal null} it is recommended instead to either use {@link #sortedBy(Comparator)}
     * or pass {@code comparator} as {@code name} instead to get a better verification error message.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sortedBy(null, *)                                                      =&gt; FAIL
     * Verifier.verify(*).not().sortedBy(null, *)                                                =&gt; FAIL
     * Verifier.verify((Object[]) null).sortedBy(*, *)                                           =&gt; FAIL
     * Verifier.verify(new Object[0]).sortedBy(*, *)                                             =&gt; PASS
     * Verifier.verify(new Object[]{123}).sortedBy(*, *)                                         =&gt; PASS
     * Verifier.verify(new Object[]{987, 654, 321}).sortedBy((o1, o2) -&gt; o1.compareTo(o2), *) =&gt; FAIL
     * Verifier.verify(new Object[]{123, 456, 789}).sortedBy((o1, o2) -&gt; o1.compareTo(o2), *) =&gt; PASS
     * </pre>
     *
     * @param comparator
     *         the {@code Comparator} to be used to verify the sort order of the elements within the value
     * @param name
     *         the optional name used to represent {@code comparator} (may be {@literal null})
     * @return A reference to this {@link BaseSortableCollectionVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code comparator} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #sortedBy(Comparator)
     */
    public V sortedBy(final Comparator<E> comparator, final Object name) {
        Verifier.verify(comparator, "comparator")
            .not().nulled();

        final Collection<E> value = getCollection(verification().getValue());
        boolean result = true;

        if (value == null) {
            result = false;
        } else if (value.size() >= 2) {
            final Iterator<E> iterator = value.iterator();
            E previous = iterator.next();

            while (iterator.hasNext()) {
                final E current = iterator.next();
                if (comparator.compare(previous, current) > 0) {
                    result = false;
                    break;
                }

                previous = current;
            }
        }

        verification().report(result, MessageKeys.SORTED_BY, name);

        return chain();
    }

    /**
     * <p>
     * The {@link MessageKey MessageKeys} that are used by {@link BaseSortableCollectionVerifier}.
     * </p>
     *
     * @since 0.2.0
     */
    enum MessageKeys implements MessageKey {

        SORTED_BY("io.skelp.verifier.type.base.BaseSortableCollectionVerifier.sortedBy");

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
