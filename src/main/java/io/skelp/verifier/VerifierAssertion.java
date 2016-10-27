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
package io.skelp.verifier;

import io.skelp.verifier.type.base.BaseCollectionVerifier;

/**
 * <p>
 * Asserts that a given value is valid based on custom criteria.
 * </p>
 *
 * @param <T>
 *         the type of the value being verified
 * @author Alasdair Mercer
 * @see CustomVerifier#that(VerifierAssertion)
 * @see BaseCollectionVerifier#thatAll(VerifierAssertion)
 * @see BaseCollectionVerifier#thatAny(VerifierAssertion)
 */
public interface VerifierAssertion<T> {

    /**
     * <p>
     * Returns whether the specified {@code value} is valid based on the custom criteria of this {@link
     * VerifierAssertion}.
     * </p>
     *
     * @param value
     *         the value to be verified
     * @return {@literal true} if {@code value} is valid; otherwise {@literal false}.
     */
    boolean verify(T value);
}
