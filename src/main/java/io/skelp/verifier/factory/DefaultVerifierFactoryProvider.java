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

import io.skelp.verifier.message.factory.DefaultMessageFormatterFactory;
import io.skelp.verifier.message.factory.MessageFormatterFactory;
import io.skelp.verifier.verification.factory.DefaultVerificationFactory;
import io.skelp.verifier.verification.factory.VerificationFactory;

/**
 * The default implementation of {@link VerifierFactoryProvider} which provides the default implementations of each
 * factory instance.
 *
 * @author Alasdair Mercer
 */
public final class DefaultVerifierFactoryProvider implements VerifierFactoryProvider {

    private final CustomVerifierFactory customVerifierFactory = new DefaultCustomVerifierFactory();
    private final MessageFormatterFactory messageFormatterFactory = new DefaultMessageFormatterFactory();
    private final VerificationFactory verificationFactory = new DefaultVerificationFactory();

    @Override
    public CustomVerifierFactory getCustomVerifierFactory() {
        return customVerifierFactory;
    }

    @Override
    public MessageFormatterFactory getMessageFormatterFactory() {
        return messageFormatterFactory;
    }

    @Override
    public VerificationFactory getVerificationFactory() {
        return verificationFactory;
    }
}
