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

import java.util.Collection;

import io.skelp.verifier.type.base.BaseSortableCollectionVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link BaseSortableCollectionVerifier} which can be used to verify a {@code Collection} value
 * and its elements.
 * </p>
 *
 * @param <E>
 *         the type of the elements contained within the value being verified
 * @author Alasdair Mercer
 */
public final class CollectionVerifier<E> extends BaseSortableCollectionVerifier<E, Collection<E>, CollectionVerifier<E>> {

    /**
     * <p>
     * Creates an instance of {@link CollectionVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public CollectionVerifier(final Verification<Collection<E>> verification) {
        super(verification);
    }

    @Override
    protected Collection<E> getCollection(final Collection<E> value) {
        return value;
    }

    @Override
    protected int getSize(final Collection<E> value) {
        return value.size();
    }
}
