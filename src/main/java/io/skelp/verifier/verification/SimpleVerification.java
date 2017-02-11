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
package io.skelp.verifier.verification;

import java.util.Locale;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.message.MessageKey;
import io.skelp.verifier.message.MessageSource;
import io.skelp.verifier.message.formatter.Formatter;
import io.skelp.verifier.message.formatter.FormatterProvider;
import io.skelp.verifier.message.locale.LocaleContext;
import io.skelp.verifier.verification.report.KeyMessageHolder;
import io.skelp.verifier.verification.report.MessageHolder;
import io.skelp.verifier.verification.report.ReportExecutor;
import io.skelp.verifier.verification.report.StringMessageHolder;

/**
 * <p>
 * A simple immutable implementation of {@link Verification}.
 * </p>
 *
 * @param <T>
 *         the type of the value being verified
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public final class SimpleVerification<T> implements Verification<T> {

    private final FormatterProvider formatterProvider;
    private final LocaleContext localeContext;
    private final MessageSource messageSource;
    private final Object name;
    private boolean negated;
    private final ReportExecutor reportExecutor;
    private final T value;

    /**
     * <p>
     * Creates an instance of {@link SimpleVerification} based on the {@code value} and optional {@code name} provided.
     * </p>
     *
     * @param localeContext
     *         the current {@link LocaleContext}
     * @param messageSource
     *         the {@link MessageSource} to be used to lookup and/or format the messages for any {@link
     *         VerifierException VerifierExceptions}
     * @param formatterProvider
     *         the {@link FormatterProvider} to be used to lookup {@link Formatter Formatters} to be used to format
     *         objects
     * @param reportExecutor
     *         the {@link ReportExecutor} to be used to report verification results
     * @param value
     *         the value being verified
     * @param name
     *         the optional name used to represent {@code value}
     */
    public SimpleVerification(final LocaleContext localeContext, final MessageSource messageSource, final FormatterProvider formatterProvider, final ReportExecutor reportExecutor, final T value, final Object name) {
        this.localeContext = localeContext;
        this.messageSource = messageSource;
        this.formatterProvider = formatterProvider;
        this.reportExecutor = reportExecutor;
        this.value = value;
        this.name = name;
    }

    @Override
    public <V> Verification<V> copy(final V value, final Object name) {
        return new SimpleVerification<>(localeContext, messageSource, formatterProvider, reportExecutor, value, name);
    }

    @Override
    public Formatter getFormatter(final Object obj) {
        return formatterProvider.getFormatter(obj);
    }

    @Override
    public String getMessage(final MessageKey key, final Object... args) {
        return messageSource.getMessage(this, key, args);
    }

    @Override
    public String getMessage(final String message, final Object... args) {
        return messageSource.getMessage(this, message, args);
    }

    @Override
    public SimpleVerification<T> report(final boolean result, final MessageKey key, final Object... args) {
        return report(result, new KeyMessageHolder(key, args));
    }

    @Override
    public SimpleVerification<T> report(final boolean result, final String message, final Object... args) {
        return report(result, new StringMessageHolder(message, args));
    }

    private SimpleVerification<T> report(final boolean result, final MessageHolder messageHolder) {
        try {
            reportExecutor.execute(this, result, messageHolder);
        } finally {
            setNegated(false);
        }

        return this;
    }

    @Override
    public Locale getLocale() {
        return localeContext.getLocale();
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
