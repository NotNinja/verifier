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

import io.skelp.verifier.type.base.BaseSortableCollectionVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
 *
 * @param <E>
 * @author Alasdair Mercer
 */
public final class ArrayVerifier<E> extends BaseSortableCollectionVerifier<E, E[], ArrayVerifier<E>> {

    /**
     * TODO: Document
     *
     * @param verification
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
