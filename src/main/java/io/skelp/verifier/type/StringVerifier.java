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

import java.util.regex.Pattern;
import io.skelp.verifier.Verification;
import io.skelp.verifier.VerifierException;

/**
 * TODO: Document
 *
 * @param <V>
 * @author Alasdair Mercer
 */
public class StringVerifier<V extends StringVerifier> extends ComparableVerifier<V> {

  /**
   * TODO: Document
   *
   * @param verification
   */
  public StringVerifier(final Verification verification) {
    super(verification);
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V blank() {
    final String value = (String) verification.getValue();
    final boolean result = value == null || value.trim().isEmpty();
    verification.check(result, "be blank");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param charSequence
   * @return
   * @throws VerifierException
   */
  public V contain(final CharSequence charSequence) {
    final String value = (String) verification.getValue();
    final boolean result = value != null && value.contains(charSequence);
    verification.check(result, "contain %s", charSequence);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V empty() {
    final String value = (String) verification.getValue();
    final boolean result = value == null || value.isEmpty();
    verification.check(result, "be empty");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param charSequence
   * @return
   * @throws VerifierException
   */
  public V endWith(final CharSequence charSequence) {
    final String value = (String) verification.getValue();
    final boolean result = value != null && value.endsWith(charSequence.toString());
    verification.check(result, "end with %s", charSequence);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param other
   * @return
   * @throws VerifierException
   */
  public V equalToIgnoreCase(final CharSequence other) {
    final String value = (String) verification.getValue();
    final boolean result = other == null ? value == null : value.equalsIgnoreCase(other.toString());
    verification.check(result, "be equal to %s (ignore case)", other);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param charSequence
   * @return
   * @throws VerifierException
   */
  public V match(final CharSequence charSequence) {
    final String value = (String) verification.getValue();
    final boolean result = value != null && value.matches(charSequence.toString());
    verification.check(result, "match %s", charSequence);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param pattern
   * @return
   * @throws VerifierException
   */
  public V match(final Pattern pattern) {
    final String value = (String) verification.getValue();
    final boolean result = value != null && pattern.matcher(value).matches();
    verification.check(result, "match %s", pattern);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param charSequence
   * @return
   * @throws VerifierException
   */
  public V startWith(final CharSequence charSequence) {
    final String value = (String) verification.getValue();
    final boolean result = value != null && value.startsWith(charSequence.toString());
    verification.check(result, "start with %s", charSequence);

    return chain();
  }
}
