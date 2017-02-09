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
package io.skelp.verifier.verification.report;

import io.skelp.verifier.message.MessageKey;
import io.skelp.verifier.message.MessageSource;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An immutable implementation of {@link MessageHolder} that contains the {@link MessageKey} relating to a localized
 * message message and format arguments which are later passed to
 * {@link MessageSource#getMessage(Verification, MessageKey, Object[])}.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public final class KeyMessageHolder implements MessageHolder {

    private final Object[] args;
    private final MessageKey key;

    /**
     * <p>
     * Creates an instance of {@link KeyMessageHolder} for the optional message {@code key} and format {@code args}
     * provided.
     * </p>
     *
     * @param key
     *         the optional {@link MessageKey} which provides a more detailed localized explanation of what was
     *         verified
     * @param args
     *         the optional format arguments which are used to format the localized message
     */
    public KeyMessageHolder(final MessageKey key, final Object[] args) {
        this.key = key;
        this.args = args;
    }

    @Override
    public String getMessage(final Verification<?> verification) {
        return verification.getMessageSource().getMessage(verification, key, args);
    }
}
