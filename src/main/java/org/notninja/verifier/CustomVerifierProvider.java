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
package org.notninja.verifier;

import org.notninja.verifier.service.Weighted;
import org.notninja.verifier.verification.Verification;

/**
 * <p>
 * Provides {@link CustomVerifier} instances to be used to verify custom data types based on their class and the current
 * {@link Verification}.
 * </p>
 * <p>
 * {@code CustomVerifierProviders} are registered via Java's SPI, so in order to register a custom
 * {@code CustomVerifierProvider} projects should contain should create a
 * {@code org.notninja.verifier.CustomVerifierProvider} file within {@code META-INF/services} listing the class
 * reference for each custom {@code CustomVerifierProvider} (e.g.
 * {@code com.example.verifier.MyCustomCustomVerifierProvider}) on separate lines. {@code CustomVerifierProviders} are
 * also {@link Weighted}, which means that they are loaded in priority order (the lower the weight, the higher the
 * priority). Verifier has a built-in default {@code CustomVerifierProvider} which is given a low priority (i.e.
 * {@value #DEFAULT_IMPLEMENTATION_WEIGHT}) to allow custom implementations to be easily ordered around it.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public interface CustomVerifierProvider extends Weighted {

    /**
     * <p>
     * Returns an instance of the specified {@link CustomVerifier} class based on the {@code verification} provided.
     * </p>
     *
     * @param cls
     *         the {@link CustomVerifier} class for which an instance is to be returned
     * @param verification
     *         the current {@link Verification}
     * @param <T>
     *         the type of the value to be verified
     * @param <V>
     *         the type of the {@link CustomVerifier}
     * @return The {@link CustomVerifier} instance of the given {@code cls} and using {@code verification}.
     * @throws VerifierException
     *         If a problem occurs while providing the {@link CustomVerifier} instance.
     */
    <T, V extends CustomVerifier<T, V>> V getCustomVerifier(Class<V> cls, Verification<T> verification);
}
