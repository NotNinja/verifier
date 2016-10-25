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
package io.skelp.verifier.verification;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.message.MessageFormatter;
import io.skelp.verifier.message.factory.MessageFormatterFactory;

/**
 * The default implementation of {@link Verification}.
 * <p>
 * Instead depending on a {@link MessageFormatter} instance which may never be used, this implementation depends on a
 * {@link MessageFormatterFactory} instance which will be used to create a {@link MessageFormatter} instance only when
 * required.
 *
 * @param <T>
 *         the type of the value being verified
 * @author Alasdair Mercer
 */
public class DefaultVerification<T> implements Verification<T> {

    private MessageFormatter messageFormatter;
    private final MessageFormatterFactory messageFormatterFactory;
    private final Object name;
    private boolean negated;
    private final T value;

    /**
     * Creates an instance of {@link DefaultVerification} based on the {@code value} and optional {@code name} provided.
     * <p>
     * {@code messageFormatterFactory} will be used to create a {@link MessageFormatter} instance only when
     * required via {@link #getMessageFormatter()}.
     *
     * @param messageFormatterFactory
     *         the {@link MessageFormatterFactory} which will be used to create an {@link MessageFormatter} instance
     *         if/when required
     * @param value
     *         the value being verified
     * @param name
     *         the optional name used to represent {@code value}
     */
    public DefaultVerification(final MessageFormatterFactory messageFormatterFactory, final T value, final Object name) {
        this.messageFormatterFactory = messageFormatterFactory;
        this.value = value;
        this.name = name;
    }

    @Override
    public DefaultVerification<T> check(final boolean result, final String message, final Object... args) throws VerifierException {
        final boolean wasNegated = negated;
        negated = false;

        if (result && wasNegated || !result && !wasNegated) {
            throw new VerifierException(getMessageFormatter().format(this, message, args));
        }

        return this;
    }

    @Override
    public MessageFormatter getMessageFormatter() throws VerifierException {
        if (messageFormatter == null) {
            messageFormatter = messageFormatterFactory.create();
        }

        return messageFormatter;
    }

    @Override
    public Object getName() {
        return name;
    }

    @Override
    public boolean isNegated() {
        return negated;
    }

    @Override
    public void setNegated(final boolean negated) {
        this.negated = negated;
    }

    @Override
    public T getValue() {
        return value;
    }
}
