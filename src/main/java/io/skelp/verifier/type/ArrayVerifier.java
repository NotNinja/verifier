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

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.type.base.BaseCollectionVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
 *
 * @param <E>
 * @author Alasdair Mercer
 */
public final class ArrayVerifier<E> extends BaseCollectionVerifier<E, E[], ArrayVerifier<E>> {

    /**
     * TODO: Document
     *
     * @param verification
     */
    public ArrayVerifier(final Verification<E[]> verification) {
        super(verification);
    }

    /**
     * TODO: Document
     *
     * @param comparator
     * @return
     * @throws VerifierException
     */
    public ArrayVerifier<E> sorted(final Comparator<E> comparator) throws VerifierException {
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
    public ArrayVerifier<E> sorted(final Comparator<E> comparator, final Object name) throws VerifierException {
        if (comparator == null) {
            throw new VerifierException("comparator must not be null");
        }

        final E[] value = verification().getValue();
        boolean result = true;

        if (value == null) {
            result = false;
        } else if (value.length >= 2) {
            E previous = value[0];
            final int length = value.length;

            for (int i = 1; i < length; i++) {
                final E current = value[i];
                if (comparator.compare(previous, current) > 0) {
                    result = false;
                    break;
                }

                previous = current;
            }
        }

        verification().check(result, "be sorted by '%s'", name);

        return this;
    }

    @Override
    protected Collection<E> getCollection(final E[] value) {
        return value != null ? Arrays.asList(value) : null;
    }

    @Override
    protected int getSize(final E[] value) {
        return value.length;
    }

    @Override
    protected boolean isEmpty(final E[] value) {
        return value.length == 0;
    }

    @Override
    protected boolean isEqualTo(final E[] value, final Object other) {
        return value == other || Arrays.deepEquals(new Object[]{value}, new Object[]{other});
    }
}
