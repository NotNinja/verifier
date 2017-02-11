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

import io.skelp.verifier.AbstractCustomVerifier;
import io.skelp.verifier.CustomVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * The most basic implementation of {@link AbstractCustomVerifier} which can be used to verify an {@code Object} value.
 * </p>
 * <p>
 * For a lot of built-in data types another, more specific, {@link CustomVerifier} implementation may also exist which
 * perhaps comes with other useful verification methods. However, {@code ObjectVerifier} is useful for cases when one
 * doesn't exist and for custom objects, possibly domain-specific.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class ObjectVerifier extends AbstractCustomVerifier<Object, ObjectVerifier> {

    /**
     * <p>
     * Creates an instance of {@link ObjectVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public ObjectVerifier(final Verification<Object> verification) {
        super(verification);
    }
}
