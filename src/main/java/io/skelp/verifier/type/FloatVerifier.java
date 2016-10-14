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

import io.skelp.verifier.verification.Verification;
import io.skelp.verifier.VerifierException;
import io.skelp.verifier.type.base.BaseComparableVerifier;
import io.skelp.verifier.type.base.BaseNumberVerifier;

/**
 * TODO: Document
 *
 * @author Alasdair Mercer
 */
public final class FloatVerifier extends BaseComparableVerifier<Float, FloatVerifier> implements BaseNumberVerifier<Float, FloatVerifier> {

  /**
   * TODO: Document
   *
   * @param verification
   */
  public FloatVerifier(final Verification<Float> verification) {
    super(verification);
  }

  @Override
  public FloatVerifier even() throws VerifierException {
    final Float value = verification.getValue();
    final boolean result = value != null && value % 2F == 0;

    verification.check(result, "be even");

    return this;
  }

  @Override
  public FloatVerifier falsehood() throws VerifierException {
    final Float value = verification.getValue();
    final boolean result = value != null && value == 0F;

    verification.check(result, "be false");

    return this;
  }

  @Override
  public FloatVerifier negative() throws VerifierException {
    final Float value = verification.getValue();
    final boolean result = value != null && value < 0F;

    verification.check(result, "be negative");

    return this;
  }

  @Override
  public FloatVerifier odd() throws VerifierException {
    final Float value = verification.getValue();
    final boolean result = value != null && value % 2 != 0F;

    verification.check(result, "be odd");

    return this;
  }

  @Override
  public FloatVerifier one() throws VerifierException {
    final Float value = verification.getValue();
    final boolean result = value != null && value == 1F;

    verification.check(result, "be one");

    return this;
  }

  @Override
  public FloatVerifier positive() throws VerifierException {
    final Float value = verification.getValue();
    final boolean result = value != null && value >= 0F;

    verification.check(result, "be positive");

    return this;
  }

  @Override
  public FloatVerifier truth() throws VerifierException {
    final Float value = verification.getValue();
    final boolean result = value != null && value == 1F;

    verification.check(result, "be true");

    return this;
  }

  @Override
  public FloatVerifier zero() throws VerifierException {
    final Float value = verification.getValue();
    final boolean result = value != null && value == 0F;

    verification.check(result, "be zero");

    return this;
  }
}
