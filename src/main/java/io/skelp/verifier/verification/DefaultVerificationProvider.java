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

import io.skelp.verifier.message.MessageSource;
import io.skelp.verifier.message.MessageSourceProvider;
import io.skelp.verifier.message.locale.LocaleContext;
import io.skelp.verifier.message.locale.LocaleContextProvider;
import io.skelp.verifier.service.Services;

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
        final LocaleContext localeContext = Services.findFirstNonNullForWeightedService(LocaleContextProvider.class, LocaleContextProvider::getLocaleContext);
        final MessageSource messageSource = Services.findFirstNonNullForWeightedService(MessageSourceProvider.class, MessageSourceProvider::getMessageSource);

        return new SimpleVerification<>(localeContext, messageSource, value, name);
    }

    @Override
    public int getWeight() {
        return DEFAULT_IMPLEMENTATION_WEIGHT;
    }
}
