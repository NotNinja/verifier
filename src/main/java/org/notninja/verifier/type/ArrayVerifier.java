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
package org.notninja.verifier.type;

import java.util.Arrays;
import java.util.Collection;

import org.notninja.verifier.type.base.BaseSortableCollectionVerifier;
import org.notninja.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link BaseSortableCollectionVerifier} which can be used to verify a array value and its
 * elements.
 * </p>
 * <p>
 * {@code ArrayVerifier} also changes how {@link #equalTo(Object)} and the other related methods work so that they check
 * that the value is <i>deeply equal</i> to the other object. This provides a more natural and expected behavior for
 * these methods when dealing with arrays.
 * </p>
 *
 * @param <E>
 *         the type of the elements contained within the value being verified
 * @author Alasdair Mercer
 * @see Arrays#deepEquals(Object[], Object[])
 */
public final class ArrayVerifier<E> extends BaseSortableCollectionVerifier<E, E[], ArrayVerifier<E>> {

    /**
     * <p>
     * Creates an instance of {@link ArrayVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public ArrayVerifier(final Verification<E[]> verification) {
        super(verification);
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
    protected boolean isEqualTo(final E[] value, final Object other) {
        return value == other || Arrays.deepEquals(new Object[]{value}, new Object[]{other});
    }
}
