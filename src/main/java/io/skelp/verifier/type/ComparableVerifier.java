/*
 * Copyright (C) 2017 Alasdair Mercer, Skelp
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

import io.skelp.verifier.type.base.BaseComparableVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * The most basic implementation of {@link BaseComparableVerifier} which can be used to verify any {@code Comparable}
 * value.
 * </p>
 * <p>
 * For a lot of built-in {@code Comparable} data types another, more specific, {@link BaseComparableVerifier}
 * implementation may also exist which perhaps comes with other useful verification methods. However,
 * {@code ComparableVerifier} is useful for cases when one doesn't exist and for custom {@code Comparable} objects,
 * possibly domain-specific.
 * </p>
 *
 * @param <T>
 *         the {@code Comparable} type of the value being verified
 * @author Alasdair Mercer
 */
public final class ComparableVerifier<T extends Comparable<? super T>> extends BaseComparableVerifier<T, ComparableVerifier<T>> {

    /**
     * <p>
     * Creates an instance of {@link ComparableVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public ComparableVerifier(final Verification<T> verification) {
        super(verification);
    }
}
