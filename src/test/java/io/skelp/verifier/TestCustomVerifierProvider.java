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
package io.skelp.verifier;

import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link CustomVerifierProvider} that is intended to be used for test purposes only by allowing
 * tests to register a delegate (normally a mock).
 * </p>
 * <p>
 * Individual tests are also responsible for ensuring that the delegate is cleared in their tear down to avoid
 * potentially affecting other unit tests.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class TestCustomVerifierProvider implements CustomVerifierProvider {

    private static CustomVerifierProvider delegate;

    /**
     * <p>
     * Returns the delegate for this {@link TestCustomVerifierProvider}.
     * </p>
     *
     * @return The delegate {@link CustomVerifierProvider} or {@literal null} if none has been specified.
     */
    public static CustomVerifierProvider getDelegate() {
        return delegate;
    }

    /**
     * <p>
     * Sets the delegate for this {@link TestCustomVerifierProvider} to {@code delegate}.
     * </p>
     *
     * @param delegate
     *         the delegate {@link CustomVerifierProvider} to be set (may be {@literal null})
     */
    public static void setDelegate(CustomVerifierProvider delegate) {
        TestCustomVerifierProvider.delegate = delegate;
    }

    @Override
    public <T, V extends CustomVerifier<T, V>> V getCustomVerifier(Class<V> cls, Verification<T> verification) {
        return delegate != null ? delegate.getCustomVerifier(cls, verification) : null;
    }

    @Override
    public int getWeight() {
        return 0;
    }
}
