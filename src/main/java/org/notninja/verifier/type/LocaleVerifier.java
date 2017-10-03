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
package org.notninja.verifier.type;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.notninja.verifier.AbstractCustomVerifier;
import org.notninja.verifier.VerifierException;
import org.notninja.verifier.message.MessageKey;
import org.notninja.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link AbstractCustomVerifier} which can be used to verify a {@code Locale} value.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class LocaleVerifier extends AbstractCustomVerifier<Locale, LocaleVerifier> {

    /**
     * <p>
     * Creates an instance of {@link LocaleVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public LocaleVerifier(final Verification<Locale> verification) {
        super(verification);
    }

    /**
     * <p>
     * Verifies that the value is an available {@code Locale}.
     * </p>
     * <pre>
     * Verifier.verify((Locale) null).available()            =&gt; FAIL
     * Verifier.verify(Locale.US).available()                =&gt; PASS
     * Verifier.verify(new Locale("foo", "BAR")).available() =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link LocaleVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public LocaleVerifier available() {
        final Locale value = verification().getValue();
        final boolean result = LazyHolder.AVAILABLE_LOCALES.contains(value);

        verification().report(result, MessageKeys.AVAILABLE);

        return this;
    }

    /**
     * <p>
     * Verifies that the value has the {@code country} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).country(null)                      =&gt; FAIL
     * Verifier.verify((Locale) null).country(*)             =&gt; FAIL
     * Verifier.verify(new Locale("en", "")).country("")     =&gt; PASS
     * Verifier.verify(new Locale("en", "US")).country("GB") =&gt; FAIL
     * Verifier.verify(new Locale("en", "US")).country("US") =&gt; PASS
     * </pre>
     *
     * @param country
     *         the country to compare against that of the value (may be {@literal null})
     * @return A reference to this {@link LocaleVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public LocaleVerifier country(final String country) {
        final Locale value = verification().getValue();
        final boolean result = value != null && value.getCountry().equals(country);

        verification().report(result, MessageKeys.COUNTRY, country);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is the default {@code Locale}.
     * </p>
     * <pre>
     * Verifier.verify((Locale) null).defaulted()            =&gt; FAIL
     * Verifier.verify(Locale.getDefault()).defaulted()      =&gt; PASS
     * Verifier.verify(new Locale("foo", "BAR")).defaulted() =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link LocaleVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @since 0.2.0
     */
    public LocaleVerifier defaulted() {
        final Locale value = verification().getValue();
        final boolean result = Locale.getDefault().equals(value);

        verification().report(result, MessageKeys.DEFAULTED);

        return this;
    }

    /**
     * <p>
     * Verifies that the value has the {@code language} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).language(null)                      =&gt; FAIL
     * Verifier.verify((Locale) null).language(*)             =&gt; FAIL
     * Verifier.verify(new Locale("en", "US")).language("fr") =&gt; FAIL
     * Verifier.verify(new Locale("en", "US")).language("en") =&gt; PASS
     * </pre>
     *
     * @param language
     *         the language to compare against that of the value (may be {@literal null})
     * @return A reference to this {@link LocaleVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public LocaleVerifier language(final String language) {
        final Locale value = verification().getValue();
        final boolean result = value != null && value.getLanguage().equals(language);

        verification().report(result, MessageKeys.LANGUAGE, language);

        return this;
    }

    /**
     * <p>
     * Verifies that the value has the {@code script} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).script(null)                    =&gt; FAIL
     * Verifier.verify((Locale) null).script(*)           =&gt; FAIL
     * Verifier.verify(new Locale("en", "US")).script("") =&gt; PASS
     * Verifier.verify(latinLocale).script("Arab")        =&gt; FAIL
     * Verifier.verify(latinLocale).script("Latn")        =&gt; PASS
     * </pre>
     *
     * @param script
     *         the script to compare against that of the value (may be {@literal null})
     * @return A reference to this {@link LocaleVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public LocaleVerifier script(final String script) {
        final Locale value = verification().getValue();
        final boolean result = value != null && value.getScript().equals(script);

        verification().report(result, MessageKeys.SCRIPT, script);

        return this;
    }

    /**
     * <p>
     * Verifies that the value has the {@code variant} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).variant(null)                                        =&gt; FAIL
     * Verifier.verify((Locale) null).variant(*)                               =&gt; FAIL
     * Verifier.verify(new Locale("en", "GB")).variant("")                     =&gt; PASS
     * Verifier.verify(new Locale("en", "GB", "scotland")).variant("scouse")   =&gt; FAIL
     * Verifier.verify(new Locale("en", "GB", "scotland")).variant("scotland") =&gt; PASS
     * </pre>
     *
     * @param variant
     *         the variant to compare against that of the value (may be {@literal null})
     * @return A reference to this {@link LocaleVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public LocaleVerifier variant(final String variant) {
        final Locale value = verification().getValue();
        final boolean result = value != null && value.getVariant().equals(variant);

        verification().report(result, MessageKeys.VARIANT, variant);

        return this;
    }

    /**
     * <p>
     * The {@link MessageKey MessageKeys} that are used by {@link LocaleVerifier}.
     * </p>
     *
     * @since 0.2.0
     */
    enum MessageKeys implements MessageKey {

        AVAILABLE("org.notninja.verifier.type.LocaleVerifier.available"),
        COUNTRY("org.notninja.verifier.type.LocaleVerifier.country"),
        DEFAULTED("org.notninja.verifier.type.LocaleVerifier.defaulted"),
        LANGUAGE("org.notninja.verifier.type.LocaleVerifier.language"),
        SCRIPT("org.notninja.verifier.type.LocaleVerifier.script"),
        VARIANT("org.notninja.verifier.type.LocaleVerifier.variant");

        private final String code;

        MessageKeys(final String code) {
            this.code = code;
        }

        @Override
        public String code() {
            return code;
        }
    }

    private static class LazyHolder {

        static final Set<Locale> AVAILABLE_LOCALES;

        static {
            final List<Locale> availableLocales = Arrays.asList(Locale.getAvailableLocales());
            AVAILABLE_LOCALES = Collections.unmodifiableSet(new HashSet<>(availableLocales));
        }
    }
}
