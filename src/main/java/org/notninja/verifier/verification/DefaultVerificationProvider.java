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
package org.notninja.verifier.verification;

import org.notninja.verifier.message.MessageSource;
import org.notninja.verifier.message.MessageSourceProvider;
import org.notninja.verifier.message.formatter.FormatterProvider;
import org.notninja.verifier.message.locale.LocaleContext;
import org.notninja.verifier.message.locale.LocaleContextProvider;
import org.notninja.verifier.service.Services;
import org.notninja.verifier.verification.report.ReportExecutor;
import org.notninja.verifier.verification.report.ReportExecutorProvider;

/**
 * <p>
 * The default implementation of {@link VerificationProvider} which provides an instance of {@link SimpleVerification}
 * and uses other providers to provide other dependencies.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public final class DefaultVerificationProvider implements VerificationProvider {

    @Override
    public <T> Verification<T> getVerification(final T value, final Object name) {
        final FormatterProvider formatterProvider = Services.getWeightedService(FormatterProvider.class);
        final LocaleContext localeContext = Services.findFirstNonNullForWeightedService(LocaleContextProvider.class, LocaleContextProvider::getLocaleContext);
        final MessageSource messageSource = Services.findFirstNonNullForWeightedService(MessageSourceProvider.class, MessageSourceProvider::getMessageSource);
        final ReportExecutor reportExecutor = Services.findFirstNonNullForWeightedService(ReportExecutorProvider.class, ReportExecutorProvider::getReportExecutor);

        return new SimpleVerification<>(localeContext, messageSource, formatterProvider, reportExecutor, value, name);
    }

    @Override
    public int getWeight() {
        return DEFAULT_IMPLEMENTATION_WEIGHT;
    }
}
