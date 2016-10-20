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
import java.util.Comparator;

import io.skelp.verifier.AbstractCustomVerifier;
import io.skelp.verifier.VerifierException;
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
 *
 * @param <T>
 * @author Alasdair Mercer
 */
public final class ArrayVerifier<T> extends AbstractCustomVerifier<T[], ArrayVerifier<T>> {

    /**
     * TODO: Document
     *
     * @param verification
     */
    public ArrayVerifier(final Verification<T[]> verification) {
        super(verification);
    }

    /**
     * TODO: Document
     *
     * @param item
     * @return
     * @throws VerifierException
     */
    public ArrayVerifier<T> contain(final T item) throws VerifierException {
        final T[] value = getVerification().getValue();
        final boolean result = value != null && Arrays.asList(value).contains(item);

        getVerification().check(result, "contain '%s'", item);

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public ArrayVerifier<T> empty() throws VerifierException {
        final T[] value = getVerification().getValue();
        final boolean result = value == null || value.length == 0;

        getVerification().check(result, "be empty");

        return this;
    }

    @Override
    protected boolean isEqualTo(final T[] value, final Object other) {
        return value == other || Arrays.deepEquals(new Object[]{value}, new Object[]{other});
    }

    /**
     * TODO: Document
     *
     * @param length
     * @return
     * @throws VerifierException
     */
    public ArrayVerifier<T> length(final int length) throws VerifierException {
        final T[] value = getVerification().getValue();
        final boolean result = value == null ? length == 0 : value.length == length;

        getVerification().check(result, "have length of '%d'", length);

        return this;
    }

    /**
     * TODO: Document
     *
     * @param comparator
     * @return
     * @throws VerifierException
     */
    public ArrayVerifier<T> sorted(final Comparator<T> comparator) throws VerifierException {
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
    public ArrayVerifier<T> sorted(final Comparator<T> comparator, final Object name) throws VerifierException {
        if (comparator == null) {
            throw new VerifierException("comparator must not be null");
        }

        final T[] value = getVerification().getValue();
        boolean result = true;

        if (value == null) {
            result = false;
        } else if (value.length >= 2) {
            T previous = value[0];
            final int length = value.length;

            for (int i = 1; i < length; i++) {
                final T current = value[i];
                if (comparator.compare(previous, current) > 0) {
                    result = false;
                    break;
                }

                previous = current;
            }
        }

        getVerification().check(result, "be sorted by '%s'", name);

        return this;
    }
}
