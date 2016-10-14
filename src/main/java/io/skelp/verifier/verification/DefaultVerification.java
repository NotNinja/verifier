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
package io.skelp.verifier.verification;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.message.MessageFormatter;

/**
 * TODO: Document
 *
 * @param <T>
 * @author Alasdair Mercer
 */
public class DefaultVerification<T> implements Verification<T> {

  private final MessageFormatter messageFormatter;
  private final Object name;
  private boolean negated;
  private final T value;

  /**
   * TODO: Document
   *
   * @param messageFormatter
   * @param value
   * @param name
   */
  public DefaultVerification(final MessageFormatter messageFormatter, final T value, final Object name) {
    this.messageFormatter = messageFormatter;
    this.value = value;
    this.name = name;
  }

  @Override
  public DefaultVerification<T> check(final boolean result, final String message, final Object... args) throws VerifierException {
    if (result && negated || !result && !negated) {
      throw new VerifierException(messageFormatter.format(this, message, args));
    }

    negated = false;

    return this;
  }

  @Override
  public MessageFormatter getMessageFormatter() {
    return messageFormatter;
  }

  @Override
  public Object getName() {
    return name;
  }

  @Override
  public boolean isNegated() {
    return negated;
  }

  @Override
  public void setNegated(final boolean negated) {
    this.negated = negated;
  }

  @Override
  public T getValue() {
    return value;
  }
}
