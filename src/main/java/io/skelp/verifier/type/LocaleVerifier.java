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
import io.skelp.verifier.Verification;
import io.skelp.verifier.VerifierException;

/**
 * TODO: Document
 *
 * @param <V>
 * @author Alasdair Mercer
 */
public class LocaleVerifier<V extends LocaleVerifier> extends BaseTypeVerifier<V> {

  /**
   * TODO: Document
   *
   * @param verification
   */
  public LocaleVerifier(final Verification verification) {
    super(verification);
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V available() {
    final Locale value = (Locale) verification.getValue();
    final boolean result = LazyHolder.AVAILABLE_LOCALES.contains(value);

    verification.check(result, "be available");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param country
   * @return
   * @throws VerifierException
   */
  public V country(final String country) {
    final Locale value = (Locale) verification.getValue();
    final boolean result = value != null && value.getCountry().equals(country);

    verification.check(result, "be country '%s'", country);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V defaulting() {
    final Locale value = (Locale) verification.getValue();
    final boolean result = Locale.getDefault().equals(value);

    verification.check(result, "be default");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param language
   * @return
   * @throws VerifierException
   */
  public V language(final String language) {
    final Locale value = (Locale) verification.getValue();
    final boolean result = value != null && value.getLanguage().equals(language);

    verification.check(result, "be language '%s'", language);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param script
   * @return
   * @throws VerifierException
   */
  public V script(final String script) {
    final Locale value = (Locale) verification.getValue();
    final boolean result = value != null && value.getScript().equals(script);

    verification.check(result, "be script '%s'", script);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param variant
   * @return
   * @throws VerifierException
   */
  public V variant(final String variant) {
    final Locale value = (Locale) verification.getValue();
    final boolean result = value != null && value.getVariant().equals(variant);

    verification.check(result, "be variant '%s'", variant);

    return chain();
  }

  private static class LazyHolder {

    static final Set<Locale> AVAILABLE_LOCALES;

    static {
      final List<Locale> availableLocales = Arrays.asList(Locale.getAvailableLocales());
      AVAILABLE_LOCALES = Collections.unmodifiableSet(new HashSet<>(availableLocales));
    }
  }
}
