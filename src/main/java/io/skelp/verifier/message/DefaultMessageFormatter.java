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
package io.skelp.verifier.message;

import io.skelp.verifier.Verification;

/**
 * TODO: Document
 *
 * @author Alasdair Mercer
 */
public class DefaultMessageFormatter implements MessageFormatter {

  /** TODO: Document */
  public static final String DEFAULT_MESSAGE = "valid";
  /** TODO: Document */
  public static final String DEFAULT_MESSAGE_NEGATED = "invalid";
  /** TODO: Document */
  public static final String DEFAULT_NAME = "Value";

  @Override
  public String format(final Verification<?> verification, final String message, final Object... args) {
    final StringBuilder buffer = new StringBuilder();
    buffer.append(verification.getName() != null ? verification.getName() : DEFAULT_NAME);
    buffer.append(" must ");
    if (verification.isNegated()) {
      buffer.append("not ");
    }
    if (message != null) {
      buffer.append(String.format(message, args));
    } else {
      buffer.append(!verification.isNegated() ? DEFAULT_MESSAGE : DEFAULT_MESSAGE_NEGATED);
    }
    buffer.append(": ");
    buffer.append(verification.getValue());

    return buffer.toString();
  }
}
