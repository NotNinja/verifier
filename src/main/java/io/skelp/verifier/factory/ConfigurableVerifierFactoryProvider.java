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
package io.skelp.verifier.factory;

import io.skelp.verifier.CustomVerifier;
import io.skelp.verifier.message.MessageFormatter;
import io.skelp.verifier.message.factory.MessageFormatterFactory;
import io.skelp.verifier.verification.Verification;
import io.skelp.verifier.verification.factory.VerificationFactory;

/**
 * <p>
 * A configurable implementation of {@link VerifierFactoryProvider} where factory instances can be changed as bean
 * properties.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class ConfigurableVerifierFactoryProvider implements VerifierFactoryProvider {

    private CustomVerifierFactory customVerifierFactory;
    private MessageFormatterFactory messageFormatterFactory;
    private VerificationFactory verificationFactory;

    @Override
    public CustomVerifierFactory getCustomVerifierFactory() {
        return customVerifierFactory;
    }

    /**
     * <p>
     * Sets the factory instance for creating {@link CustomVerifier CustomVerifiers} to {@code customVerifierFactory}.
     * </p>
     *
     * @param customVerifierFactory
     *         the instance of {@link CustomVerifierFactory} to be set
     */
    public void setCustomVerifierFactory(final CustomVerifierFactory customVerifierFactory) {
        this.customVerifierFactory = customVerifierFactory;
    }

    @Override
    public MessageFormatterFactory getMessageFormatterFactory() {
        return messageFormatterFactory;
    }

    /**
     * <p>
     * Sets the factory instance for creating {@link MessageFormatter MessageFormatters} to {@code
     * messageFormatterFactory}.
     * </p>
     *
     * @param messageFormatterFactory
     *         the instance of {@link MessageFormatterFactory} to be set
     */
    public void setMessageFormatterFactory(final MessageFormatterFactory messageFormatterFactory) {
        this.messageFormatterFactory = messageFormatterFactory;
    }

    @Override
    public VerificationFactory getVerificationFactory() {
        return verificationFactory;
    }

    /**
     * <p>
     * Sets the factory instance for creating {@link Verification Verification} to {@code verificationFactory}.
     * </p>
     *
     * @param verificationFactory
     *         the instance of {@link VerificationFactory} to be set
     */
    public void setVerificationFactory(final VerificationFactory verificationFactory) {
        this.verificationFactory = verificationFactory;
    }
}
