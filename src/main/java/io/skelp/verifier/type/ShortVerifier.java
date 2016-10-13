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

import io.skelp.verifier.Verification;

/**
 * TODO: Document
 *
 * @author Alasdair Mercer
 */
public class ShortVerifier<V extends ShortVerifier> extends ComparableVerifier<V> implements NumberVerifier<V, Short> {

  /**
   * TODO: Document
   *
   * @param verification
   */
  public ShortVerifier(final Verification verification) {
    super(verification);
  }

  @Override
  public V even() {
    final Short value = (Short) verification.getValue();
    final boolean result = value != null && value % 2 == 0;

    verification.check(result, "be even");

    return chain();
  }

  @Override
  public V falsehood() {
    final Short value = (Short) verification.getValue();
    final boolean result = value != null && value == 0;

    verification.check(result, "be false");

    return chain();
  }

  @Override
  public V negative() {
    final Short value = (Short) verification.getValue();
    final boolean result = value != null && value < 0;

    verification.check(result, "be negative");

    return chain();
  }

  @Override
  public V odd() {
    final Short value = (Short) verification.getValue();
    final boolean result = value != null && value % 2 != 0;

    verification.check(result, "be odd");

    return chain();
  }

  @Override
  public V one() {
    final Short value = (Short) verification.getValue();
    final boolean result = value != null && value == 1;

    verification.check(result, "be one");

    return chain();
  }

  @Override
  public V positive() {
    final Short value = (Short) verification.getValue();
    final boolean result = value != null && value >= 0;

    verification.check(result, "be positive");

    return chain();
  }

  @Override
  public V truth() {
    final Short value = (Short) verification.getValue();
    final boolean result = value != null && value == 0;

    verification.check(result, "be true");

    return chain();
  }

  @Override
  public V zero() {
    final Short value = (Short) verification.getValue();
    final boolean result = value != null && value == 0;

    verification.check(result, "be zero");

    return chain();
  }
}
