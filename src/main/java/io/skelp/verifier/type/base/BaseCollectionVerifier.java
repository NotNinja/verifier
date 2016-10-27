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
 * TODO: Document
 *
 * @param <E>
 * @param <T>
 * @author Alasdair Mercer
 */
public abstract class BaseCollectionVerifier<E, T, V extends BaseCollectionVerifier<E, T, V>> extends AbstractCustomVerifier<T, V> {

    /**
     * TODO: Document
     *
     * @param verification
     */
    public BaseCollectionVerifier(final Verification<T> verification) {
        super(verification);
    }

    /**
     * TODO: Document
     *
     * @param item
     * @return
     * @throws VerifierException
     */
    public V contain(final E item) throws VerifierException {
        final Collection<E> value = getCollection(verification().getValue());
        final boolean result = value != null && value.contains(item);

        verification().check(result, "contain '%s'", item);

        return chain();
    }

    /**
     * TODO: Document
     *
     * @param items
     * @return
     * @throws VerifierException
     */
    public V containAll(final E... items) throws VerifierException {
        final Collection<E> value = getCollection(verification().getValue());
        final boolean result = value != null && matchAll(items, new Function<Boolean, E>() {
            @Override
            public Boolean apply(final E input) {
                return value.contains(input);
            }
        });

        verification().check(result, "contain all %s", verification().getMessageFormatter().formatArray(items));

        return chain();
    }

    /**
     * TODO: Document
     *
     * @param items
     * @return
     * @throws VerifierException
     */
    public V containAny(final E... items) throws VerifierException {
        final Collection<E> value = getCollection(verification().getValue());
        final boolean result = value != null && matchAny(items, new Function<Boolean, E>() {
            @Override
            public Boolean apply(final E input) {
                return value.contains(input);
            }
        });

        verification().check(result, "contain any %s", verification().getMessageFormatter().formatArray(items));

        return chain();
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public V empty() throws VerifierException {
        final T value = verification().getValue();
        final boolean result = value == null || isEmpty(value);

        verification().check(result, "be empty");

        return chain();
    }

    /**
     * TODO: Document
     *
     * @param size
     * @return
     * @throws VerifierException
     */
    public V size(final int size) throws VerifierException {
        final T value = verification().getValue();
        final boolean result = value == null ? size == 0 : getSize(value) == size;

        verification().check(result, "have size of '%d'", size);

        return chain();
    }

    /**
     * TODO: Document
     *
     * @param assertion
     * @return
     * @throws VerifierException
     */
    public V thatAll(final VerifierAssertion<E> assertion) throws VerifierException {
        return thatAll(assertion, null);
    }

    /**
     * TODO: Document
     *
     * @param assertion
     * @param message
     * @param args
     * @return
     * @throws VerifierException
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
     * TODO: Document
     *
     * @param assertion
     * @return
     * @throws VerifierException
     */
    public V thatAny(final VerifierAssertion<E> assertion) throws VerifierException {
        return thatAny(assertion, null);
    }

    /**
     * TODO: Document
     *
     * @param assertion
     * @param message
     * @param args
     * @return
     * @throws VerifierException
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
     * TODO: Document
     *
     * @param value
     * @return
     */
    protected abstract Collection<E> getCollection(T value);

    /**
     * TODO: Document
     *
     * @param value
     * @return
     */
    protected abstract int getSize(T value);

    /**
     * TODO: Document
     *
     * @param value
     * @return
     */
    protected abstract boolean isEmpty(T value);
}
