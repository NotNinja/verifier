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
    public LocaleVerifier available() throws VerifierException {
        final Locale value = verification().getValue();
        final boolean result = LazyHolder.AVAILABLE_LOCALES.contains(value);

        verification().check(result, "be available");

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
    public LocaleVerifier country(final String country) throws VerifierException {
        final Locale value = verification().getValue();
        final boolean result = value != null && value.getCountry().equals(country);

        verification().check(result, "be country '%s'", country);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is the default {@code Locale}.
     * </p>
     * <pre>
     * Verifier.verify((Locale) null).defaulting()            =&gt; FAIL
     * Verifier.verify(Locale.getDefault()).defaulting()      =&gt; PASS
     * Verifier.verify(new Locale("foo", "BAR")).defaulting() =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link LocaleVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public LocaleVerifier defaulting() throws VerifierException {
        final Locale value = verification().getValue();
        final boolean result = Locale.getDefault().equals(value);

        verification().check(result, "be default");

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
    public LocaleVerifier language(final String language) throws VerifierException {
        final Locale value = verification().getValue();
        final boolean result = value != null && value.getLanguage().equals(language);

        verification().check(result, "be language '%s'", language);

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
    public LocaleVerifier script(final String script) throws VerifierException {
        final Locale value = verification().getValue();
        final boolean result = value != null && value.getScript().equals(script);

        verification().check(result, "be script '%s'", script);

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
