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
package io.skelp.verifier.verification.factory;

import io.skelp.verifier.factory.VerifierFactoryException;
import io.skelp.verifier.message.MessageFormatter;
import io.skelp.verifier.message.factory.MessageFormatterFactory;
import io.skelp.verifier.verification.Verification;

/**
 * A factory for creating instances of {@link Verification} based on a given value and optional name.
 *
 * @author Alasdair Mercer
 */
public interface VerificationFactory {

    /**
     * Creates an instance of {@link Verification} based on the {@code value} and optional {@code name} provided.
     * <p>
     * The {@code messageFormatterFactory} is required to create a {@link MessageFormatter} while leaving it up to the
     * implementation when this instance is created as it may not be used.
     *
     * @param messageFormatterFactory
     *         the {@link MessageFormatterFactory} to be used to create an instance of {@link MessageFormatter}
     * @param value
     *         the value being verified
     * @param name
     *         the optional name for the value being verified
     * @param <T>
     *         the type of the value being verified
     * @return The newly created {@link Verification} based on {@code value} and {@code name}.
     * @throws VerifierFactoryException
     *         If a problem occurs while creating the {@link Verification} instance.
     */
    <T> Verification<T> create(MessageFormatterFactory messageFormatterFactory, T value, Object name) throws VerifierFactoryException;
}
