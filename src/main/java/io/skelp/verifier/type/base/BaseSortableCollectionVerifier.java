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
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
 *
 * @param <E>
 * @param <T>
 * @param <V>
 * @author Alasdair Mercer
 */
public abstract class BaseSortableCollectionVerifier<E, T, V extends BaseSortableCollectionVerifier<E, T, V>> extends BaseCollectionVerifier<E, T, V> {

    /**
     * TODO: Document
     *
     * @param verification
     */
    public BaseSortableCollectionVerifier(final Verification<T> verification) {
        super(verification);
    }

    /**
     * TODO: Document
     *
     * @param comparator
     * @return
     * @throws VerifierException
     */
    public V sorted(final Comparator<E> comparator) throws VerifierException {
        return sorted(comparator, comparator);
    }

    /**
     * TODO: Document
     *
     * @param comparator
     * @param name
     * @return
     * @throws VerifierException
     */
    public V sorted(final Comparator<E> comparator, final Object name) throws VerifierException {
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

        verification().check(result, "be sorted by '%s'", name);

        return chain();
    }
}
