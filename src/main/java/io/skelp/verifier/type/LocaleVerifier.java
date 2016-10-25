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
package io.skelp.verifier.type;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import io.skelp.verifier.AbstractCustomVerifier;
import io.skelp.verifier.VerifierException;
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
 *
 * @author Alasdair Mercer
 */
public final class LocaleVerifier extends AbstractCustomVerifier<Locale, LocaleVerifier> {

    /**
     * TODO: Document
     *
     * @param verification
     */
    public LocaleVerifier(final Verification<Locale> verification) {
        super(verification);
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public LocaleVerifier available() throws VerifierException {
        final Locale value = verification().getValue();
        final boolean result = LazyHolder.AVAILABLE_LOCALES.contains(value);

        verification().check(result, "be available");

        return this;
    }

    /**
     * TODO: Document
     *
     * @param country
     * @return
     * @throws VerifierException
     */
    public LocaleVerifier country(final String country) throws VerifierException {
        final Locale value = verification().getValue();
        final boolean result = value != null && value.getCountry().equals(country);

        verification().check(result, "be country '%s'", country);

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public LocaleVerifier defaulting() throws VerifierException {
        final Locale value = verification().getValue();
        final boolean result = Locale.getDefault().equals(value);

        verification().check(result, "be default");

        return this;
    }

    /**
     * TODO: Document
     *
     * @param language
     * @return
     * @throws VerifierException
     */
    public LocaleVerifier language(final String language) throws VerifierException {
        final Locale value = verification().getValue();
        final boolean result = value != null && value.getLanguage().equals(language);

        verification().check(result, "be language '%s'", language);

        return this;
    }

    /**
     * TODO: Document
     *
     * @param script
     * @return
     * @throws VerifierException
     */
    public LocaleVerifier script(final String script) throws VerifierException {
        final Locale value = verification().getValue();
        final boolean result = value != null && value.getScript().equals(script);

        verification().check(result, "be script '%s'", script);

        return this;
    }

    /**
     * TODO: Document
     *
     * @param variant
     * @return
     * @throws VerifierException
     */
    public LocaleVerifier variant(final String variant) throws VerifierException {
        final Locale value = verification().getValue();
        final boolean result = value != null && value.getVariant().equals(variant);

        verification().check(result, "be variant '%s'", variant);

        return this;
    }

    private static class LazyHolder {

        static final Set<Locale> AVAILABLE_LOCALES;

        static {
            final List<Locale> availableLocales = Arrays.asList(Locale.getAvailableLocales());
            AVAILABLE_LOCALES = Collections.unmodifiableSet(new HashSet<>(availableLocales));
        }
    }
}
