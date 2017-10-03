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
package org.notninja.verifier.message;

import java.util.Locale;

import org.notninja.verifier.VerifierException;

/**
 * <p>
 * A runtime exception which is thrown by {@link MessageSource MessageSources} when a {@link MessageKey} cannot be
 * found.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public class NoSuchMessageException extends VerifierException {

    /**
     * <p>
     * Creates a new instance of {@link NoSuchMessageException} for the {@code key} and {@code locale} provided.
     * </p>
     *
     * @param key
     *         the {@link MessageKey} that could not be found (may be {@literal null})
     * @param locale
     *         the current {@code Locale}
     */
    public NoSuchMessageException(final MessageKey key, final Locale locale) {
        this(key != null ? key.code() : null, locale);
    }

    /**
     * <p>
     * Creates a new instance of {@link NoSuchMessageException} for the {@link MessageKey} {@code code} and
     * {@code locale} provided.
     * </p>
     *
     * @param code
     *         the code of the {@link MessageKey} that could not be found (may be {@literal null})
     * @param locale
     *         the current {@code Locale}
     */
    public NoSuchMessageException(final String code, final Locale locale) {
        super("No message found under key '" + code + "' for locale '" + locale + "'");
    }
}
